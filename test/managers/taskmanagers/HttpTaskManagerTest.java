package managers.taskmanagers;

import model.Epic;
import model.Subtask;
import model.Task;
import servers.KVServer;
import model.enums.Status;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class HttpTaskManagerTest {
protected KVServer server;

private HttpTaskManager manager;

@BeforeEach
public void loadInitialConditions() throws IOException {
        server = new KVServer();
        server.start();
        manager = new HttpTaskManager();
        }

@AfterEach
    void serverStop() {
            server.stop();
            }

@Test
    void loadFromServerTest() {

        Task task1 = manager.createTask(new Task(
                1,
                "Task",
                Status.NEW,
                "task",
                LocalDateTime.now(),
                0)
        );
        Epic epic1 = manager.createEpic(new Epic(
                2,
                "Epic",
                Status.NEW,
                "Description_epic",
                LocalDateTime.now(),
                0));
        Subtask subtask1 = manager.createSubtask(new Subtask(
                3,
                "Subtask",
                Status.NEW,
                "sub",
                LocalDateTime.now(),
                0,
                epic1.getTaskId()
        ));
            manager.getSingleTask(task1.getTaskId());
            manager.getSingleEpic(epic1.getTaskId());
            manager.getSingleSubtask(subtask1.getTaskId());
            manager.load();
            Collection<Task> tasks = manager.getAllTasks();

            assertNotNull(tasks);
            assertEquals(1, tasks.size());

            List<Task> history = manager.getHistory();

            assertNotNull(history);
            assertEquals(3, history.size());
            }
            }