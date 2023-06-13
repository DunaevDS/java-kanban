import managers.Managers;
import managers.taskmanagers.HttpTaskManager;
import managers.taskmanagers.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;
import servers.KVServer;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {

        new KVServer().start();
        TaskManager httpManager = Managers.getDefault();

/*      InMemoryTaskManager httpManager = new InMemoryTaskManager();  //локальный тест
        HttpTaskServer manager = new HttpTaskServer(httpManager);*/   //локальный тест
        Task task1 = httpManager.createTask(new Task(
                1,
                "Task",
                Status.NEW,
                "task",
                LocalDateTime.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        LocalDateTime.now().getHour(),
                        LocalDateTime.now().getMinute(),
                        LocalDateTime.now().getSecond()),
                0)
        );
        Task task2 = httpManager.createTask(new Task(
                2,
                "Task",
                Status.NEW,
                "task",
                LocalDateTime.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        LocalDateTime.now().getHour(),
                        LocalDateTime.now().getMinute(),
                        LocalDateTime.now().getSecond()).plusMinutes(2),
                0)
        );
        Epic epic1 = httpManager.createEpic(new Epic(
                3,
                "Epic",
                Status.NEW,
                "Description_epic",
                LocalDateTime.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        LocalDateTime.now().getHour(),
                        LocalDateTime.now().getMinute(),
                        LocalDateTime.now().getSecond()),
                0)
        );
        Subtask subtask1 = httpManager.createSubtask(new Subtask(
                4,
                "Subtask",
                Status.NEW,
                "sub",
                LocalDateTime.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        LocalDateTime.now().getHour(),
                        LocalDateTime.now().getMinute(),
                        LocalDateTime.now().getSecond()).plusSeconds(2),
                0,
                epic1.getTaskId())
        );
        Subtask subtask2 = httpManager.createSubtask(new Subtask(
                5,
                "Subtask",
                Status.NEW,
                "sub",
                LocalDateTime.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        LocalDateTime.now().getHour(),
                        LocalDateTime.now().getMinute(),
                        LocalDateTime.now().getSecond()).plusSeconds(5),
                0,
                epic1.getTaskId())
        );
        //manager.start(); // локальный тест

        httpManager.getSingleTask(task1.getTaskId());
        httpManager.getSingleEpic(epic1.getTaskId());
        httpManager.getSingleSubtask(subtask1.getTaskId());
        httpManager.getHistory();

        HttpTaskManager newHttpManager = new HttpTaskManager();
        newHttpManager.load();
        newHttpManager.getPrioritizedTasks();

        System.out.println(newHttpManager.getHistory()); // проверка
        System.out.println(newHttpManager.getPrioritizedTasks()); // проверка
    }
}
