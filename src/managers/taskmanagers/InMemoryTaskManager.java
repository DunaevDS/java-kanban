package managers.taskmanagers;

import managers.Managers;
import managers.historymanagers.HistoryManager;
import managers.taskmanagers.exceptions.IntersectionException;
import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;
import model.enums.Type;
import model.utils.TaskComparator;


import java.time.Duration;
import java.util.stream.IntStream;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id;
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final TaskComparator taskComparator = new TaskComparator();
    protected final Set<Task> prioritizedTasks = new TreeSet<>(taskComparator);

    protected static HistoryManager historyManager = Managers.getDefaultHistory();
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";



    private int getNextId() {
        return ++id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected Task newTask() {
        return new Task(
                0,
                "Task",
                Status.NEW,
                "Description,task1",
                LocalDateTime.now(),
                0
        );
    }

    protected Epic newEpic() {
        return new Epic(
                0,
                "Epic",
                Status.NEW,
                "Description_epic1",
                LocalDateTime.now(),
                0
        );
    }

    protected Subtask newSubtask(Epic epic) {
        return new Subtask(0,
                "Subtask",
                Status.NEW,
                "Subtask1",
                LocalDateTime.now(),
                0,
                epic.getTaskId()
        );
    }

    @Override
    public Task createTask(Task task) {
        task.setTaskId(getNextId());
        tasks.put(task.getTaskId(), task);
        addToPrioritizedTasks(task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setTaskId(getNextId());
        epics.put(epic.getTaskId(), epic);
        return epic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        subtask.setTaskId(getNextId());
        subtasks.put(subtask.getTaskId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtask(subtask.getTaskId());
        addToPrioritizedTasks(subtask);
        updateEpicStatus(subtask.getEpicId());
        return subtask;
    }

    @Override
    public Collection<Task> getAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println(ANSI_RED + "------> No tasks <------" + ANSI_RESET);
            return null;
        }
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Collection<Epic> getAllEpics() {

        if (epics.isEmpty()) {
            System.out.println(ANSI_RED + "------> No epics <------" + ANSI_RESET);
            return null;
        }
        return new ArrayList<>(epics.values());
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {

        if (subtasks.isEmpty()) {
            System.out.println(ANSI_RED + "------> No subtasks <------" + ANSI_RESET);
            return null;
        }
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllTasks() {

        for (Task task : tasks.values()) {
            historyManager.remove(task.getTaskId());
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {

        for (Subtask subtask : subtasks.values()) {
            historyManager.remove(subtask.getTaskId());
        }

        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getTaskId());
        }

        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {

        for (int value : epics.keySet()) {
            Epic epic = epics.get(value);
            epic.removeAllSubtasks();
        }

        for (Subtask subtask : subtasks.values()) {
            historyManager.remove(subtask.getTaskId());
        }

        subtasks.clear();
    }

    @Override
    public void deleteSingleTask(int id) {
        Task singleTask = tasks.get(id);
        if (singleTask != null) {
            tasks.remove(id);
            historyManager.remove(id);
            prioritizedTasks.removeIf(t -> t.getTaskId() == id);
        } else {
            System.out.println(ANSI_RED + "Task ID=" + id + " is not existing" + ANSI_RESET);
        }
    }

    @Override
    public void deleteSingleEpic(int epicID) {
        Epic epic = epics.get(epicID);

        if (epic == null) {
            System.out.println(ANSI_RED + "Epic ID=" + epicID + " is not existing" + ANSI_RESET);
            return;
        }

        for (Integer id : epic.getSubs()) {
            prioritizedTasks.removeIf(t -> t.getTaskId() == id);
            subtasks.remove(id);
            historyManager.remove(id);
        }
        epics.remove(epicID);
        historyManager.remove(epicID);
    }

    @Override
    public void deleteSingleSubtask(int id) {
        Subtask subtask = subtasks.get(id);

        if (subtask == null) {
            System.out.println(ANSI_RED + "Subtask ID=" + id + " is not existing" + ANSI_RESET);
            return;
        }

        Epic epic = epics.get(subtask.getEpicId());
        prioritizedTasks.removeIf(t -> t.getTaskId() == id);
        epic.removeSubtask(id);
        subtasks.remove(id);
        historyManager.remove(id);

        updateEpicStatus(subtask.getEpicId());
    }

    @Override
    public Type getType(int id) {
        if (tasks.get(id) != null) return tasks.get(id).getType();
        else if (epics.get(id) != null) return epics.get(id).getType();
        else return subtasks.get(id).getType();

    }


    @Override
    public Task getSingleTask(int id) {
        Task singleTask = tasks.get(id);
        if (singleTask != null) {
            historyManager.add(singleTask);
            return singleTask;
        } else {
            System.out.println(ANSI_RED + "Task is null" + ANSI_RESET);
            return null;
        }
    }

    @Override
    public Epic getSingleEpic(int id) {
        Epic singleEpic = epics.get(id);
        if (singleEpic != null) {
            historyManager.add(singleEpic);
            return singleEpic;
        } else {
            System.out.println(ANSI_RED + "Epic is null" + ANSI_RESET);
            return null;
        }
    }

    @Override
    public Subtask getSingleSubtask(int id) {
        Subtask singleSubtask = subtasks.get(id);
        if (singleSubtask != null) {

            historyManager.add(singleSubtask);
            return singleSubtask;
        } else {
            System.out.println(ANSI_RED + "Subtask is null" + ANSI_RESET);
            return null;
        }
    }

    @Override
    public Task updateSingleTask(Task task) {
        if (task == null) {
            System.out.println(ANSI_RED + "Task is null" + ANSI_RESET);
            return null;
        } else {
            tasks.put(task.getTaskId(), task);
            addToPrioritizedTasks(task);
            return task;
        }
    }

    @Override
    public Epic updateSingleEpic(Epic epic) {
        if (epic == null) {
            System.out.println(ANSI_RED + "Epic is null" + ANSI_RESET);
            return null;
        } else {
            epics.put(epic.getTaskId(), epic);
            updateEpicStatus(epic.getTaskId());
            return epic;
        }
    }

    @Override
    public Subtask updateSingleSubtask(Subtask subtask) {
        if (subtask == null) {
            System.out.println(ANSI_RED + "Subtask is null" + ANSI_RESET);
            return null;
        } else {
            subtasks.put(subtask.getTaskId(), subtask);
            addToPrioritizedTasks(subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epic.updateSubtask(subtask.getTaskId());
            updateEpicStatus(subtask.getEpicId());
            return subtask;
        }
    }

    @Override
    public void printAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
            return;
        }
        for (int id : tasks.keySet()) {
            Task value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    @Override
    public void printAllEpics() {
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
            return;
        }
        for (int id : epics.keySet()) {
            Epic value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    @Override
    public void printAllSubtasks() {
        if (subtasks.isEmpty()) {
            System.out.println("Список подзадач пуст.");
            return;
        }
        for (int id : subtasks.keySet()) {
            Subtask value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateEpicStatus(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            throw new NullPointerException(ANSI_RED + "Epic is null" + ANSI_RESET);
        } else {
            LocalDateTime startTime = subtasks.get(epic.getSubs().get(0)).getStartTime();
            LocalDateTime endTime = subtasks.get(epic.getSubs().get(0)).getEndTime();
            int counterNew = 0;
            int counterDone = 0;
            for (int value : epic.getSubs()) {

                Subtask subtask = subtasks.get(value);

                if (subtask.getStatus() == Status.NEW) counterNew++;
                if (subtask.getStatus() == Status.DONE) counterDone++;

                if (subtask.getStartTime().isBefore(startTime))
                    startTime = subtask.getStartTime();

                if (subtask.getEndTime().isAfter(endTime))
                    endTime = subtask.getEndTime();

            }
            epic.setStartTime(startTime);
            epic.setEndTime(endTime);
            epic.setDuration(Duration.between(startTime, endTime).toMinutes());

            if (epic.getSubs().size() == counterNew) {
                epic.setStatus(Status.NEW);
                return;
            } else if (epic.getSubs().size() == counterDone) {
                epic.setStatus(Status.DONE);
                return;
            }
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    // TreeSet => ArrayList
    public ArrayList<Task> getPrioritizedTasks() {
        return new ArrayList<Task>(prioritizedTasks);
    }

    private void addToPrioritizedTasks(Task task) {
        prioritizedTasks.add(task);
        checkIntersection();
    }

    // проверка на пересечение
    private void checkIntersection() {
        ArrayList<Task> prioritizedTasks = getPrioritizedTasks();
        IntStream.range(1, prioritizedTasks.size())
                .forEach(i -> {
                    Task prioritizedTask = prioritizedTasks.get(i);
                    if (prioritizedTask.getStartTime().isBefore(prioritizedTasks.get(i - 1).getEndTime()))
                        throw new IntersectionException("Пересечение между "
                                + prioritizedTask
                                + " и "
                                + prioritizedTasks.get(i - 1));
                });
    }

    // печать приоритетного списка
    public void printPrioritizedTasks() {

        System.out.println("СПИСОК ПРИОРИТЕТНЫХ ЗАДАЧ: ");
        prioritizedTasks.forEach(System.out::println);

    }
}


