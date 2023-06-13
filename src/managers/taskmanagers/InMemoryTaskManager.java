package managers.taskmanagers;

import managers.Managers;
import managers.historymanagers.HistoryManager;
import managers.taskmanagers.exceptions.IntersectionException;
import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;
import model.utils.TaskComparator;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private int id;
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final TaskComparator taskComparator = new TaskComparator();
    protected final Set<Task> prioritizedTasks = new TreeSet<>(taskComparator);

    protected final HistoryManager historyManager = Managers.getDefaultHistory();
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
        updateEpic(subtask.getEpicId());
        return subtask;
    }

    @Override
    public Collection<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Collection<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
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
            return;
        }

        Epic epic = epics.get(subtask.getEpicId());
        prioritizedTasks.removeIf(t -> t.getTaskId() == id);
        epic.removeSubtask(id);
        subtasks.remove(id);
        historyManager.remove(id);

        updateEpic(subtask.getEpicId());
    }


    @Override
    public Task getSingleTask(int id) {
        Task singleTask = tasks.get(id);
        if (singleTask != null) {
            historyManager.add(singleTask);
            return singleTask;
        } else {
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
            return null;
        }
    }
    @Override
    public Collection<Subtask> getAllSubtasksByEpicId(int id){
        Epic singleEpic = epics.get(id);
        if (singleEpic != null) {
            historyManager.add(singleEpic);
            List<Integer> subsList = singleEpic.getSubs();
            List<Subtask> subs = new ArrayList<>();

            for (Integer value: subsList) {
                subs.add(getSingleSubtask(value));
            } return subs;
        } else return null;
    }

    @Override
    public Task updateSingleTask(Task task) {
        if (task == null) {
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
            return null;
        } else {
            epics.put(epic.getTaskId(), epic);
            updateEpic(epic.getTaskId());
            return epic;
        }
    }

    @Override
    public Subtask updateSingleSubtask(Subtask subtask) {
        if (subtask == null) {
            return null;
        } else {
            subtasks.put(subtask.getTaskId(), subtask);
            addToPrioritizedTasks(subtask);
            updateEpic(subtask.getEpicId());
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

    private void updateEpic(int id) {

        Epic epic = epics.get(id);

        if (epic == null) {
            throw new NullPointerException(ANSI_RED + "Epic is null" + ANSI_RESET);
        } else {
            updateEpicTime(epic);
            updateEpicStatus(epic);
        }
    }

    private void updateEpicStatus(Epic epic) {
        int counterNew = 0;
        int counterDone = 0;

        for (int value : epic.getSubs()) {

            Subtask subtask = subtasks.get(value);

            if (subtask.getStatus() == Status.NEW) counterNew++;
            if (subtask.getStatus() == Status.DONE) counterDone++;

        }
        if (epic.getSubs().size() == 0 && epic.getStatus() != Status.NEW) {
            return;
        } else if (epic.getSubs().size() == counterNew) {
            epic.setStatus(Status.NEW);
            return;
        } else if (epic.getSubs().size() == counterDone) {
            epic.setStatus(Status.DONE);
            return;
        }
        epic.setStatus(Status.IN_PROGRESS);
    }

    private void updateEpicTime(Epic epic) {
        LocalDateTime startTime;
        LocalDateTime endTime;

        if (epic.getSubs().isEmpty()) {
            startTime = endTime = LocalDateTime.now();
        } else {
            startTime = subtasks.get(epic.getSubs().get(0)).getStartTime();
            endTime = subtasks.get(epic.getSubs().get(0)).getEndTime();
        }
        for (int value : epic.getSubs()) {

            Subtask subtask = subtasks.get(value);
            if (subtask.getStartTime().isBefore(startTime))
                startTime = subtask.getStartTime();

            if (subtask.getEndTime().isAfter(endTime))
                endTime = subtask.getEndTime();
        }
        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        epic.setDuration(Duration.between(startTime, endTime).toMinutes());
    }

    // TreeSet => ArrayList
    @Override
    public List<Task> getPrioritizedTasks() {
        return prioritizedTasks.stream()
                .sorted(Comparator.nullsLast(taskComparator))
                .collect(Collectors.toList());
    }

    private void addToPrioritizedTasks(Task task) {
        checkIntersection(task);
        prioritizedTasks.add(task);
    }

    // проверка на пересечение
    private void checkIntersection(Task task) {
        List<Task> prioritizedTasks = getPrioritizedTasks();
        prioritizedTasks.forEach(t -> {
            if (task.getStartTime().isBefore(t.getEndTime())
                    && task.getEndTime().isAfter(t.getStartTime()))
                throw new IntersectionException("Пересечение между "
                        + t.getName()
                        + " id = "
                        + t.getTaskId()
                        + " и "
                        + prioritizedTasks.get(t.getTaskId() - 1).getName()
                        + "id = "
                        + prioritizedTasks.get(t.getTaskId() - 1).getTaskId()
                );
        });
    }

    // печать приоритетного списка
    @Override
    public void printPrioritizedTasks() {
        System.out.println("СПИСОК ПРИОРИТЕТНЫХ ЗАДАЧ: ");
        prioritizedTasks.forEach(System.out::println);
    }

    @Override
    public void removeEverything() {
        prioritizedTasks.clear();
        historyManager.clear();
        subtasks.clear();
        epics.clear();
        tasks.clear();
    }
}


