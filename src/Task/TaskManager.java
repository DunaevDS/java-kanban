package Task;


import java.util.*;

public class TaskManager {

    protected HashMap<Integer, TaskInfo> tasks = new HashMap<>();
    private static int id; // хотел изначально в отдельный класс чтобы счетчик работал и на эпики и сабтаски,
                           // но сделал просто переменную статичной.

    public TaskInfo newTask() {
        return new TaskInfo("Task1", "Description_task1");
    }

    public TaskInfo createTask(TaskInfo taskInfo) {

        taskInfo.setTaskId(getNextID());
        tasks.put(taskInfo.getTaskId(), taskInfo);

        return taskInfo;
    }

    public int getNextID() {
        return ++id;
    }

    public HashMap<Integer, TaskInfo> getAllTasks() {

        if (!tasks.isEmpty()) return tasks;
        else return null;
    }

    public void deleteSingleTask(int id) {
        tasks.remove(id);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public TaskInfo getSingleTask(int id) {
        return tasks.get(id);
    }

    public TaskInfo updateSingleTask(TaskInfo taskInfo) { // правильно ли это сделано? Не до конца понимаю каков результат
        tasks.put(taskInfo.getTaskId(), taskInfo);         // такого обновления? Передаем в метод существующую задачу,
        return taskInfo;                                   // получаем ее ID, записываем на эту же позицию этот же метод.
    }                                                      // В каких случаях это нужно будет...пока не понимаю.

}

