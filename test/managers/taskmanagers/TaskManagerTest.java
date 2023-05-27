package managers.taskmanagers;

import managers.taskmanagers.exceptions.IntersectionException;
import managers.taskmanagers.exceptions.ManagerSaveException;
import managers.historymanagers.InMemoryHistoryManager;
import managers.historymanagers.HistoryManager;
import managers.taskmanagers.TaskManager;
import model.enums.Status;
import model.enums.Type;
import org.junit.jupiter.api.Test;
import model.utils.DataTransformation;
import model.utils.TaskComparator;
import model.Subtask;
import model.Epic;
import model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest <T extends TaskManager> {

    private final Map<Integer, Task> emptyMap = new HashMap<>();
    private final List<Task> emptyList = new ArrayList<>();
    protected T manager;

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

        @Test
        public void getEpicIDTest() {

            var epic1 = manager.createEpic(newEpic());
            var subtask1 = manager.createSubtask(newSubtask(epic1));

            assertEquals(epic1.getId(), subtask1.getEpicID());

        }

        @Test
        public void setEpicEndTimeTest() {

            var epic1 = manager.createEpic(newEpic());

            epic1.setEndTime(Instant.ofEpochSecond(42));

            assertEquals(Instant.ofEpochSecond(42), epic1.getEndTime());

        }

        @Test
        public void getTaskTypeTest() {

            var task1 = manager.createTask(newTask());

            assertEquals(TaskType.TASK, task1.getTaskType());

        }

        @Test
        public void getDurationTest() {

            var task1 = manager.createTask(newTask());

            assertEquals(0, task1.getDuration());

        }

        @Test
        public void setDurationTest() {

            var task1 = manager.createTask(newTask());

            task1.setDuration(42);

            assertEquals(42, task1.getDuration());

        }

        @Test
        public void setStartTimeTest() {

            var task1 = manager.createTask(newTask());

            task1.setStartTime(Instant.ofEpochSecond(42));

            assertEquals(Instant.ofEpochSecond(42), task1.getStartTime());

        }

        @Test
        public void getTaskNameTest() {

            var task1 = manager.createTask(newTask());

            assertEquals("Task1", task1.getName());

        }

        @Test
        public void getTaskDescriptionTest() {

            var task1 = manager.createTask(newTask());

            assertEquals("Task1", task1.getDescription());

        }
    }
