package managers;

import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id;
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Task> tasks = new HashMap<>();

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public int getNextId() {
        return ++id;
    }

    public Task newTask() {
        return new Task("Task1", "Description_task1", getNextId(), Status.NEW);
    }

    public Epic newEpic() {
        return new Epic("Epic1", "Description_epic1", getNextId(), Status.NEW);

    }

    public Subtask newSubtask(Epic epic) {
        return new Subtask("Subtask1", "Subtask1", epic.getTaskId(), getNextId(), Status.NEW);
    }

    public Task createTask(Task task) {
        tasks.put(task.getTaskId(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epics.put(epic.getTaskId(), epic);
        return epic;
    }

    public Subtask createSubtask(Subtask subtask) {
        subtasks.put(subtask.getTaskId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtask(subtask.getTaskId());
        updateEpicStatus(subtask.getEpicId());
        return subtask;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> printTasks = new ArrayList<>();
        if (tasks.isEmpty()) {
            System.out.println(ANSI_RED + "------> No tasks <------" + ANSI_RESET);
            return null;
        }
        for (int value : tasks.keySet()) {
            Task task = tasks.get(value);
            printTasks.add(task);
        }
        return printTasks;
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> printEpics = new ArrayList<>();

        if (epics.isEmpty()) {
            System.out.println(ANSI_RED + "------> No epics <------" + ANSI_RESET);
            return null;
        }
        for (int value : epics.keySet()) {
            Epic epic = epics.get(value);
            printEpics.add(epic);
        }
        return printEpics;
    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> printSubtasks = new ArrayList<>();
        if (subtasks.isEmpty()) {
            System.out.println(ANSI_RED + "------> No subtasks <------" + ANSI_RESET);
            return null;
        }
        for (int value : subtasks.keySet()) {
            Subtask subtask = subtasks.get(value);
            printSubtasks.add(subtask);
        }
        return printSubtasks;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    public void deleteAllSubtasks() {
        for (int value : epics.keySet()) {
            Epic epic = epics.get(value);
            epic.removeAllSubtasks();
        }
        subtasks.clear();
    }

    public void deleteSingleTask(int id) {
        tasks.remove(id);
    }

    public void deleteSingleEpic(int epicID) {
        Epic epic = epics.get(epicID);

        if (epic == null) return;

        for (Integer id : epic.getSubs()) {
            subtasks.remove(id);
        }
        epics.remove(epicID);
    }

    public void deleteSingleSubtask(int id) {
        Subtask subtask = subtasks.get(id);

        if (subtask == null) return;

        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubtask(id);     // немного не понял зачем нужно было так сделать...ради структурирования кода?
        subtasks.remove(id);

        updateEpicStatus(subtask.getEpicId());
    }

    public Task getSingleTask(int id) {
        return tasks.get(id);
    }

    public Epic getSingleEpic(int id) {
        Epic singleEpic = epics.get(id);
        if (singleEpic != null) return singleEpic;
        else {
            System.out.println("Задачи с такой ID не существует");
            return null;
        }
    }

    public Subtask getSingleSubtask(int id) {
        return subtasks.get(id);
    }

    public Task updateSingleTask(Task task) {
        tasks.put(task.getTaskId(), task);
        return task;
    }

    public Epic updateSingleEpic(Epic updatedEpic) {

        Epic oldEpic = epics.get(updatedEpic.getTaskId());

        oldEpic.updateEpic(updatedEpic);
        epics.put(updatedEpic.getTaskId(), oldEpic);
        updateEpicStatus(oldEpic.getTaskId());
        return oldEpic;
    }

    public Subtask updateSingleSubtask(Subtask subtask) {
        subtasks.put(subtask.getTaskId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.updateSubtask(subtask.getTaskId());
        updateEpicStatus(subtask.getEpicId());
        return subtask;
    }

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

