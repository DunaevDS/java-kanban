package managers.taskmanagers;
import managers.historymanagers.HistoryManager;
import model.enums.Status;
import managers.Managers;
import model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {

    private final List<Task> emptyList = new ArrayList<>();
    private HistoryManager manager;

    @BeforeEach
    public void loadInitialConditions() {

        manager = Managers.getDefaultHistory();

    }

    @Test
    public void addTasksToHistoryTest() {

        Task task1 = new Task(
                1,
                "Task1", Status.NEW,
                "Task1",
                LocalDateTime.now(),
                0
        );
        Task task2 = new Task(
                2,
                "Task2",
                Status.NEW,
                "Task2",
                LocalDateTime.now(),
                0
        );
        Task task3 = new Task(
                3,
                "Task3",
                Status.NEW,
                "Task3",
                LocalDateTime.now(),
                0);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        assertEquals(List.of(task1, task2, task3), manager.getHistory());

    }

    @Test
    public void removeAllTasksTest() {
        Task task1 = new Task(
                1,
                "Task1", Status.NEW,
                "Task1",
                LocalDateTime.now(),
                0
        );
        Task task2 = new Task(
                2,
                "Task2",
                Status.NEW,
                "Task2",
                LocalDateTime.now(),
                0
        );
        Task task3 = new Task(
                3,
                "Task3",
                Status.NEW,
                "Task3",
                LocalDateTime.now(),
                0);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        manager.remove(1);
        manager.remove(2);
        manager.remove(3);

        assertEquals(emptyList, manager.getHistory());

    }

    @Test
    public void removeTask() {
        Task task1 = new Task(
                1,
                "Task1", Status.NEW,
                "Task1",
                LocalDateTime.now(),
                0
        );
        Task task2 = new Task(
                2,
                "Task2",
                Status.NEW,
                "Task2",
                LocalDateTime.now(),
                0
        );
        Task task3 = new Task(
                3,
                "Task3",
                Status.NEW,
                "Task3",
                LocalDateTime.now(),
                0);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        manager.remove(task2.getTaskId());

        assertEquals(List.of(task1, task3), manager.getHistory());

    }

    @Test
    public void noReplicaWhenCreateTasks() {
        Task task1 = new Task(
                1,
                "Task1", Status.NEW,
                "Task1",
                LocalDateTime.now(),
                0
        );
        Task task2 = new Task(
                2,
                "Task2",
                Status.NEW,
                "Task2",
                LocalDateTime.now(),
                0
        );
        Task task3 = new Task(
                3,
                "Task3",
                Status.NEW,
                "Task3",
                LocalDateTime.now(),
                0);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        assertEquals(List.of(task1, task2, task3), manager.getHistory());

    }

    @Test
    public void noTaskRemoveIfWrongID() {
        Task task1 = new Task(
                1,
                "Task1", Status.NEW,
                "Task1",
                LocalDateTime.now(),
                0
        );
        Task task2 = new Task(
                2,
                "Task2",
                Status.NEW,
                "Task2",
                LocalDateTime.now(),
                0
        );
        Task task3 = new Task(
                3,
                "Task3",
                Status.NEW,
                "Task3",
                LocalDateTime.now(),
                0);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        manager.remove(4);
        manager.remove(20);
        manager.remove(0);

        assertEquals(List.of(task1, task2, task3), manager.getHistory());

    }

}