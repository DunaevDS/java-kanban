package managers.taskmanagers;

import managers.Managers;
import managers.historymanagers.HistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;
import model.enums.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private static int id;
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected static HistoryManager historyManager = Managers.getDefaultHistory();
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private static int getNextId() {
        return ++id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    // ниже основные методы для создания задач.

    //Task task1 = fileBackedTasksManager.createTask(fileBackedTasksManager.newTask());
    // Здесь создается новая таска и потом она поступает в createTask у Бэк менеджера.
    protected Task newTask() {
        return new Task(getNextId(), Type.TASK, "Task", Status.NEW, "Description,task1");
    }

    protected Epic newEpic() {
        return new Epic(getNextId(), Type.EPIC, "Epic", Status.NEW, "Description_epic1");
    }

    protected Subtask newSubtask(Epic epic) {
        return new Subtask(getNextId(), Type.SUBTASK, "Subtask", Status.NEW, "Subtask1", epic.getTaskId());
    }

    @Override
    public Task createTask(Task task) {
        tasks.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epics.put(epic.getTaskId(), epic);
        return epic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        subtasks.put(subtask.getTaskId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtask(subtask.getTaskId());
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
            int counterNew = 0;
            int counterDone = 0;

            for (int value : epic.getSubs()) {

                Subtask subtask = subtasks.get(value);

                if (subtask.getStatus() == Status.NEW) counterNew++;
                if (subtask.getStatus() == Status.DONE) counterDone++;
            }
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
}

