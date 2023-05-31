package managers.taskmanagers;

import managers.historymanagers.HistoryManager;
import managers.taskmanagers.exceptions.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;

import model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import managers.Managers;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest {

    private Path filePath = Path.of("src\\test.csv");


    private FileBackedTasksManager manager;


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

    @BeforeEach
    public void loadManager() {
        manager = Managers.getDefaultFileBackedManager();

    }

    @Test
    public void shouldLoadFromFileTest() {
        Task task1 = manager.createTask(newTask());
        Epic epic1 = manager.createEpic(newEpic());
        Subtask subtask1 = manager.createSubtask(newSubtask(epic1));

        manager.getSingleTask(task1.getTaskId());
        manager.getSingleEpic(epic1.getTaskId());
        manager.getSingleSubtask(subtask1.getTaskId());
        manager = manager.load(filePath);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);

        ArrayList<Epic> epics = new ArrayList<>();
        epics.add(epic1);

        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask1);

        assertEquals(tasks, manager.getAllTasks());
        assertEquals(epics, manager.getAllEpics());
        assertEquals(subtasks, manager.getAllSubtasks());
    }

    @Test
    public void shouldThrowManagerSaveExceptionTest() {
        filePath = Path.of("src\\failTest.csv");

        assertThrows(ManagerSaveException.class, () -> manager.load(filePath));
    }
}