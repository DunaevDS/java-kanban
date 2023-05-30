package managers.historymanagers;

import model.Epic;
import model.Subtask;
import org.junit.jupiter.api.Test;
import model.enums.Status;
import managers.Managers;
import model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {

    private final List<Task> emptyList = new ArrayList<>();
    private HistoryManager manager;

    @BeforeEach
    public void initializeManagerTasksEpicsSubtasks() {

        manager = Managers.getDefaultHistory();

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

        Epic epic1 = new Epic(
                4,
                "Epic",
                Status.NEW,
                "Description_epic1",
                LocalDateTime.now(),
                0
        );

        Epic epic2 = new Epic(
                5,
                "Epic",
                Status.NEW,
                "Description_epic1",
                LocalDateTime.now(),
                0
        );

        Subtask subtask1 = new Subtask(
                6,
                "Subtask",
                Status.NEW,
                "Subtask1",
                LocalDateTime.now(),
                0,
                epic1.getTaskId()
        );

        Subtask subtask2 = new Subtask(
                7,
                "Subtask",
                Status.NEW,
                "Subtask1",
                LocalDateTime.now(),
                0,
                epic1.getTaskId()
        );

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        manager.add(epic1);
        manager.add(epic2);

        manager.add(subtask1);
        manager.add(subtask2);

        epic1.addSubtask(6);
        epic1.addSubtask(7);
    }

    @Test
    public void addTasksEpicsSubtasksToHistoryTest() {

        Task task1 = manager.getHistory().get(0);
        Task task2 = manager.getHistory().get(1);
        Task task3 = manager.getHistory().get(2);
        Epic epic1 = (Epic) manager.getHistory().get(3);
        Epic epic2 = (Epic) manager.getHistory().get(4);
        Subtask subtask1 = (Subtask) manager.getHistory().get(5);
        Subtask subtask2 = (Subtask) manager.getHistory().get(6);

        assertEquals(List.of(task1, task2, task3, epic1, epic2, subtask1, subtask2), manager.getHistory());

    }

    @Test
    public void removeAllTasksEpicsSubtasksTest() {

        Task task1 = manager.getHistory().get(0);
        Task task2 = manager.getHistory().get(1);
        Task task3 = manager.getHistory().get(2);
        Epic epic1 = (Epic) manager.getHistory().get(3);
        Epic epic2 = (Epic) manager.getHistory().get(4);
        Subtask subtask1 = (Subtask) manager.getHistory().get(5);
        Subtask subtask2 = (Subtask) manager.getHistory().get(6);


        manager.remove(task1.getTaskId());
        manager.remove(task2.getTaskId());
        manager.remove(task3.getTaskId());
        manager.remove(epic1.getTaskId());
        manager.remove(epic2.getTaskId());
        manager.remove(subtask1.getTaskId());
        manager.remove(subtask2.getTaskId());

        assertEquals(emptyList, manager.getHistory());

    }

    @Test
    public void removeOneTaskOneEpicOneSubtaskTest() {

        Task task1 = manager.getHistory().get(0);
        Task task2 = manager.getHistory().get(1);
        Task task3 = manager.getHistory().get(2);
        Epic epic1 = (Epic) manager.getHistory().get(3);
        Epic epic2 = (Epic) manager.getHistory().get(4);
        Subtask subtask1 = (Subtask) manager.getHistory().get(5);
        Subtask subtask2 = (Subtask) manager.getHistory().get(6);

        manager.remove(task2.getTaskId());
        manager.remove(epic2.getTaskId());
        manager.remove(subtask1.getTaskId());

        assertEquals(List.of(task1, task3, epic1, subtask2), manager.getHistory());

    }

    @Test
    public void noReplicaWhenCreateTasksEpicsSubtasks() {

        Task task1 = manager.getHistory().get(0);
        Task task2 = manager.getHistory().get(1);
        Task task3 = manager.getHistory().get(2);
        Epic epic1 = (Epic) manager.getHistory().get(3);
        Epic epic2 = (Epic) manager.getHistory().get(4);
        Subtask subtask1 = (Subtask) manager.getHistory().get(5);
        Subtask subtask2 = (Subtask) manager.getHistory().get(6);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.add(epic1);
        manager.add(epic2);
        manager.add(subtask1);
        manager.add(subtask2);

        assertEquals(List.of(task1, task2, task3, epic1, epic2, subtask1, subtask2), manager.getHistory());

    }

    @Test
    public void noTaskEpicSubtaskRemoveIfWrongID() {

        Task task1 = manager.getHistory().get(0);
        Task task2 = manager.getHistory().get(1);
        Task task3 = manager.getHistory().get(2);
        Epic epic1 = (Epic) manager.getHistory().get(3);
        Epic epic2 = (Epic) manager.getHistory().get(4);
        Subtask subtask1 = (Subtask) manager.getHistory().get(5);
        Subtask subtask2 = (Subtask) manager.getHistory().get(6);


        manager.remove(8);
        manager.remove(20);
        manager.remove(0);

        assertEquals(List.of(task1, task2, task3, epic1, epic2, subtask1, subtask2), manager.getHistory());

    }

}