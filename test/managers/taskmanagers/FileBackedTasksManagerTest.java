package managers.taskmanagers;

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
        manager = FileBackedTasksManager.load(filePath);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);

        ArrayList<Epic> epics = new ArrayList<>();
        epics.add(epic1);

        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask1);



        /**Нужен совет по ошибке, которая встречается в строках 93 и 95. Т.к. getAllEpics и getHistory вызываются из ИнМемори, то
        // не получается обновить endTime у созданного эпика и из-за этого он null. Далее создаем сабтаску в 69 строке и endTime у эпика
        // обновляется, но в FileBackedManager, а в ИнМемори остается null. Как лучше быть?  Пытался у нового эпика по дефолту сделать
        // локальное время, но оно различается в несколько милсек. Еще был вариант передавать в метод updateEpicStatus(int id) вместо id мапу с сабтасками.
         */
        assertEquals(Arrays.hashCode(tasks.toArray()), Arrays.hashCode(manager.getAllTasks().toArray()));
        assertEquals(Arrays.hashCode(epics.toArray()), Arrays.hashCode(manager.getAllEpics().toArray()));
        assertEquals(Arrays.hashCode(subtasks.toArray()), Arrays.hashCode(manager.getAllSubtasks().toArray()));
        assertEquals((task1.hashCode()+ epic1.hashCode()+ subtask1.hashCode()), Arrays.hashCode(manager.getHistory().toArray()));
    }

    @Test
    public void shouldThrowManagerSaveExceptionTest() {
        filePath = Path.of("src\\failTest.csv");

        assertThrows(ManagerSaveException.class, () -> FileBackedTasksManager.load(filePath));
    }
}