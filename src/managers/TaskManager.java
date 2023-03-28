package managers;

import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;
import model.enums.Type;


import java.util.*;

public class TaskManager {
    private static int id;

    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Task> tasks = new HashMap<>();


    public int getNextID() {
        return ++id;
    }

    public Task newTask() {
        return new Task("Task1", "Description_task1");
    }

    public Epic newEpic() {

        return new Epic("Epic1", "Description_epic1", Type.EPIC);

    }

    public Subtask newSubtask(Epic epic) {
        return new Subtask("Subtask1", "Subtask1", epic.getTaskId());
    }

    public Task createTask(Task task) {

        task.setTaskId(getNextID());
        tasks.put(task.getTaskId(), task);

        return task;
    }

    public Epic createEpic(Epic epic) {

        epic.setTaskId(getNextID());
        epics.put(epic.getTaskId(), epic);

        return epic;
    }

    public Subtask createSubtask(Subtask subtask) {

        subtask.setTaskId(getNextID());
        subtasks.put(subtask.getTaskId(), subtask);

        Epic epic = epics.get(subtask.getEpicID());

        epic.addSubtask(subtask);
        updateEpicStatus(subtask.getEpicID());

        return subtask;
    }

    public HashMap<Integer, Task> getAllTasks() {
        if (!tasks.isEmpty()) return tasks;
        else {
            System.out.println("No tasks");
            return null;
        }
    }

    public HashMap<Integer, Epic> getAllEpics() {
        if (!epics.isEmpty()) return epics;
        else {
            System.out.println("No epics");
            return null;
        }
    }

    public HashMap<Integer, Subtask> getAllSubtasks() {
        if (!subtasks.isEmpty()) return subtasks;
        else {
            System.out.println("No subtasks");
            return null;
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    public void deleteSingleTask(int id) {
        tasks.remove(id);
    }

    public void deleteSingleEpic(int epicID) {
        Epic epic = epics.get(epicID);

        if (epic == null)
            return;

        for (Integer id : epic.getSubs()) {
            subtasks.remove(id);
        }
        epics.remove(epicID);
    }

    public void deleteSingleSubtask(int id) {
        Subtask subtask = subtasks.get(id);

        if (subtask == null) return;

        Epic epic = epics.get(subtask.getEpicID());
        epic.getSubs().remove((Integer) (id));
        epics.put(epic.getTaskId(), epic);
        subtasks.remove(id);

        updateEpicStatus(subtask.getEpicID());
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

    public Epic updateSingleEpic(Epic epic) {
        epics.put(epic.getTaskId(), epic);
        updateEpicStatus(epic.getTaskId());
        return epic;
    }

    public Subtask updateSingleSubtask(Subtask subtask) {
        subtasks.put(subtask.getTaskId(), subtask);
        Epic epic = epics.get(subtask.getEpicID());
        updateEpicStatus(subtask.getEpicID());
        return subtask;
    }

    public void printAllTasks() {
        if (tasks.isEmpty())
            System.out.println("Список задач пуст.");

        for (int id : tasks.keySet()) {
            Task value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    public void printAllEpics() {

        if (epics.isEmpty())
            System.out.println("Список эпиков пуст.");

        for (int id : epics.keySet()) {
            Epic value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    public void printAllSubtasks() {

        if (subtasks.isEmpty())
            System.out.println("Список подзадач пуст.");

        for (int id : subtasks.keySet()) {
            Subtask value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }

    }

    public void updateEpicStatus(int id) {

        Epic epic = epics.get(id);


        int counterNew = 0;
        int counterDone = 0;


        for (int value : epic.getSubs()) {

            Subtask subtask = subtasks.get(value);

            if (subtask.getStatus() == Status.NEW)
                counterNew++;
            if (subtask.getStatus() == Status.DONE)
                counterDone++;
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

