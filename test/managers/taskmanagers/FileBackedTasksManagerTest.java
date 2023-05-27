package managers.taskmanagers;

import managers.taskmanagers.exceptions.ManagerSaveException;
import managers.taskmanagers.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import managers.Managers;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private Path filePath = Path.of("src/main/resources/results.csv");

    @BeforeEach
    public void loadManager() {

        manager = Managers.getDefaultFileBackedManager();

    }

    @Test
    public void shouldLoadFromFileTest() {

        var task1 = manager.createTask(newTask());
        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        manager.getSingleTask(task1.getTaskId());
        manager.getSingleEpic(epic1.getTaskId());
        manager.getSingleSubtask(subtask1.getTaskId());
        manager = FileBackedTasksManager.load(filePath);

        assertEquals(Map.of(task1.getTaskId(), task1), manager.getAllTasks());
        assertEquals(Map.of(epic1.getTaskId(), epic1), manager.getAllEpics());
        assertEquals(Map.of(subtask1.getTaskId(), subtask1), manager.getAllSubtasks());
        assertEquals(List.of(task1, epic1, subtask1), manager.getHistory());

    }

    @Test
    public void shouldThrowManagerSaveExceptionTest() {

        filePath = Path.of("https://r.mtdv.me/EasterEggForReview");

        assertThrows(ManagerSaveException.class, () -> FileBackedTasksManager.load(filePath));

    }
}