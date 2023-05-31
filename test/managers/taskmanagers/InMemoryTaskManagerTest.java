package managers.taskmanagers;

import managers.taskmanagers.exceptions.IntersectionException;
import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;
import model.enums.Type;
import model.utils.DataTransformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private InMemoryTaskManager manager;

    private Task newTask() {
        return new Task(
                0,
                "Task",
                Status.NEW,
                "Description,task",
                LocalDateTime.now(),
                0
        );
    }

    private Epic newEpic() {
        return new Epic(
                0,
                "Epic",
                Status.NEW,
                "Description_epic",
                LocalDateTime.now(),
                0
        );
    }

    private Subtask newSubtask(Epic epic) {
        return new Subtask(0,
                "Subtask",
                Status.NEW,
                "Subtask",
                LocalDateTime.now(),
                0,
                epic.getTaskId()
        );
    }

    @BeforeEach
    public void newManager() {
        manager = new InMemoryTaskManager();
    }

    @Test
    public void getEpicIDTest() {
        Epic epic = manager.createEpic(newEpic());
        Subtask subtask = manager.createSubtask(newSubtask(epic));

        assertEquals(epic.getTaskId(), subtask.getEpicId());
    }

    @Test
    public void setEpicEndTimeTest() {
        Epic epic = manager.createEpic(newEpic());
        epic.setEndTime(LocalDateTime.of(2000, 1, 1, 0, 0));

        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), epic.getEndTime());

    }

    @Test
    public void getTaskTypeTest() {
        Task task = manager.createTask(newTask());

        assertEquals(Type.TASK, task.getType());
    }

    @Test
    public void getDurationTest() {
        Task task = manager.createTask(newTask());

        assertEquals(0, task.getDuration());
    }

    @Test
    public void setDurationTest() {
        Task task = manager.createTask(newTask());

        task.setDuration(2);

        assertEquals(2, task.getDuration());
    }

    @Test
    public void setStartTimeTest() {
        Task task = manager.createTask(newTask());

        task.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 0));

        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), task.getStartTime());
    }

    @Test
    public void getTaskNameTest() {
        Task task = manager.createTask(newTask());

        assertEquals("Task", task.getName());
    }

    @Test
    public void getTaskDescriptionTest() {
        Task task = manager.createTask(newTask());

        assertEquals("Description,task", task.getDescription());
    }

    @Test
    public void updateTaskStateTest() {
        Task task = manager.createTask(newTask());

        task.setStatus(Status.IN_PROGRESS);
        manager.updateSingleTask(task);

        Status taskWithNewStatus = manager.getSingleTask(task.getTaskId()).getStatus();

        assertEquals(Status.IN_PROGRESS, taskWithNewStatus);
    }

    @Test
    public void updateSubtaskStateDoneTest() {
        Epic epic = manager.createEpic(newEpic());
        Subtask subtask = manager.createSubtask(newSubtask(epic));
        subtask.setStatus(Status.DONE);

        manager.updateSingleSubtask(subtask);

        Status updatedEpicState = manager.getSingleEpic(epic.getTaskId()).getStatus();
        Status updatedSubtaskState = manager.getSingleSubtask(subtask.getTaskId()).getStatus();

        assertEquals(Status.DONE, updatedEpicState);
        assertEquals(Status.DONE, updatedSubtaskState);
    }

    @Test
    public void updateEpicStateToInProgressTest() {
        Epic epic = manager.createEpic(newEpic());
        Subtask subtask = manager.createSubtask(newSubtask(epic));
        Subtask subtask2 = manager.createSubtask(newSubtask(epic));

        subtask.setStatus(Status.IN_PROGRESS);
        manager.updateSingleSubtask(subtask);
        subtask2.setStatus(Status.DONE);
        manager.updateSingleSubtask(subtask2);

        Status updatedEpicState = manager.getSingleEpic(epic.getTaskId()).getStatus();

        assertEquals(Status.IN_PROGRESS, updatedEpicState);
    }

    @Test
    public void updateEpicTest() {
        Epic epic = manager.createEpic(newEpic());

        epic.setStatus(Status.IN_PROGRESS);
        manager.updateSingleEpic(epic);

        Status updatedEpicState = manager.getSingleEpic(epic.getTaskId()).getStatus();

        assertEquals(Status.IN_PROGRESS, updatedEpicState);
    }

    @Test
    public void removeTaskTest() {
        Task task = manager.createTask(newTask());

        manager.deleteSingleTask(task.getTaskId());

        assertNull(manager.getAllTasks());
    }

    @Test
    public void updateSubtaskStateInProgressTest() {
        Epic epic = manager.createEpic(newEpic());
        Subtask subtask = manager.createSubtask(newSubtask(epic));

        subtask.setStatus(Status.IN_PROGRESS);
        manager.updateSingleSubtask(subtask);

        Status updatedEpicState = manager.getSingleEpic(epic.getTaskId()).getStatus();
        Status updatedSubtaskState = manager.getSingleSubtask(subtask.getTaskId()).getStatus();

        assertEquals(Status.IN_PROGRESS, updatedEpicState);
        assertEquals(Status.IN_PROGRESS, updatedSubtaskState);
    }

    @Test
    public void removeEpicTest() {
        Epic epic = manager.createEpic(newEpic());
        Subtask subtask = manager.createSubtask(newSubtask(epic));

        manager.deleteSingleEpic(epic.getTaskId());

        assertNull(manager.getAllEpics());
    }

    @Test
    public void calculateStartAndEndTimeOfEpicTest() {
        Epic epic = manager.createEpic(newEpic());
        Subtask subtask = manager.createSubtask(newSubtask(epic));
        Subtask subtask2 = manager.createSubtask(newSubtask(epic));

        assertEquals(subtask.getStartTime(), epic.getStartTime());
        assertEquals(subtask2.getEndTime(), epic.getEndTime());
    }

    @Test
    public void noEpicRemoveIfIncorrectIDTest() {
        Epic epic = manager.createEpic(newEpic());
        Subtask subtask = manager.createSubtask(newSubtask(epic));

        manager.deleteSingleEpic(3141);

        assertEquals(epic.hashCode(), manager.getSingleEpic(1).hashCode());
    }

    @Test
    public void tasksFromStringTest() {
        Task Task = new Task(1, "Task", Status.NEW,
                "Task", LocalDateTime.of(2000, 1, 1, 0, 0), 0);

        Task testTask = DataTransformation.fromString(
                "1,TASK,Task,NEW,\"Task\",2000-01-01T00:00,0,2000-01-01T00:00");

        assertEquals(Task, testTask);
    }

    @Test
    public void shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DataTransformation.fromString(
                "notID,TASK,Task,NEW,Task1,2000-01-01T00:00,0,2000-01-01T00:00"));
    }


    @Test
    public void returnEmptyHistoryTest() {
        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    public void returnHistoryWithTasksTest() {
        Task task = manager.createTask(newTask());
        Epic epic = manager.createEpic(newEpic());
        Subtask subtask = manager.createSubtask(newSubtask(epic));

        manager.getSingleTask(task.getTaskId());
        manager.getSingleEpic(epic.getTaskId());
        manager.getSingleSubtask(subtask.getTaskId());

        assertEquals(List.of(task, epic,subtask), manager.getHistory());
    }

    @Test
    public void shouldThrowIntersectionException() {
        assertThrows(IntersectionException.class, () -> {

            Task task = manager.createTask(new Task(
                    0,
                    "Task",
                    Status.NEW,
                    "Description,task",
                    LocalDateTime.of(2023,5,31,12,0),
                    0
            ));
            Task task2 = manager.createTask(new Task(
                    1,
                    "Task",
                    Status.NEW,
                    "Description,task",
                    LocalDateTime.of(2023,5,31,13,0),
                    30
            ));
            Task task3 = manager.createTask(new Task(
                    2,
                    "Task",
                    Status.NEW,
                    "Description,task",
                    LocalDateTime.of(2023,5,31,13,10),
                    5
            ));
        });
    }

    @AfterEach
    public void clean(){
        manager.removeEverything();
    }
}