package servers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import managers.taskmanagers.InMemoryTaskManager;
import managers.taskmanagers.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;
import model.utils.DataTransformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    private HttpTaskServer server;
    private Subtask subtask1;
    private Subtask subtask2;
    private Task task1;
    private Task task2;
    private Epic epic1;
    private Gson gson;

    @BeforeEach
    void loadInitialConditions() throws IOException {
        TaskManager manager = new InMemoryTaskManager();
        server = new HttpTaskServer(manager);
        gson = DataTransformation.createGson();
        task1 = manager.createTask(new Task(
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
        task2 = manager.createTask(new Task(
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
        epic1 = manager.createEpic(new Epic(
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
        subtask1 = manager.createSubtask(new Subtask(
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
        subtask2 = manager.createSubtask(new Subtask(
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
        manager.getSingleTask(task1.getTaskId());
        manager.getSingleTask(task2.getTaskId());
        manager.getSingleEpic(epic1.getTaskId());
        manager.getSingleSubtask(subtask1.getTaskId());
        manager.getSingleSubtask(subtask2.getTaskId());
        server.start();
    }

    @AfterEach
    void serverStop() {
        server.stop();
    }

    @Test
    void getAllTasksTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<List<Task>>() {}.getType();
        List<Task> tasks = gson.fromJson(response.body(), type);

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
    }

    @Test
    void getAllEpicsTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<List<Epic>>() {}.getType();
        List<Epic> epics = gson.fromJson(response.body(), type);

        assertNotNull(epics);
        assertEquals(1, epics.size());

    }

    @Test
    void getAllSubtasksTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<List<Subtask>>() {}.getType();
        List<Subtask> subtasks = gson.fromJson(response.body(), type);

        assertNotNull(subtasks);
        assertEquals(2, subtasks.size());
    }

    @Test
    void getSingleSubtaskTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask/epic/?id=3");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<List<Subtask>>() {}.getType();
        List<Subtask> subtasks = gson.fromJson(response.body(), type);

        assertNotNull(subtasks);
        assertEquals(2, subtasks.size());
    }

    @Test
    void getSingleTaskTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/?id=1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<Task>() {}.getType();
        Task task = gson.fromJson(response.body(), type);

        assertNotNull(task);
        assertEquals(task1, task);
    }

    @Test
    void getHistoryTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/history");

        HttpRequest request = HttpRequest.newBuilder().
                uri(uri).
                GET().
                build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<List<Task>>() {}.getType();
        List<Task> tasks = gson.fromJson(response.body(), type);

        assertNotNull(tasks);
        assertEquals(5, tasks.size());
    }

    @Test
    void getPrioritizedTasksTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<List<Task>>() {}.getType();
        List<Task> tasks = gson.fromJson(response.body(), type);

        assertNotNull(tasks);
        assertEquals(4, tasks.size());
    }

    @Test
    void getEmptyPrioritizedTasksTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());


        client = HttpClient.newHttpClient();
        uri = URI.create("http://localhost:8080/tasks");

        request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        handler = HttpResponse.BodyHandlers.ofString();
        response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }

    @Test
    void createTaskTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");

        String json = gson.toJson(task1);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());
    }

    @Test
    void createEpicTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic");

        String json = gson.toJson(epic1);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());
    }

    @Test
    void createSubtaskTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask");

        String json = gson.toJson(subtask1);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());
    }

    @Test
    void deleteTaskTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/?id=1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder().
                uri(uri).
                GET().
                build();

        response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }

    @Test
    void deleteEpicTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic/?id=3");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder().
                uri(uri).
                GET().
                build();

        response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }

    @Test
    void deleteSubtaskTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask/?id=4");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder().
                uri(uri).
                GET().
                build();

        response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }

    @Test
    void deleteTasksTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder().
                uri(uri).
                GET().
                build();

        response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }

    @Test
    void unknownEndpointTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/wrongurl/");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }
    @Test
    void unknownEndpointTest2() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/wrongurl");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }

    @Test
    void unknownEndpointTest3() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic/wrongurl");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }

    @Test
    void unknownEndpointTest4() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/wrongulr/?id=1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }

    @Test
    void unknownTaskIdTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/?id=0");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }

    @Test
    void unknownTaskIdTest2() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/?id=20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        assertEquals(404, response.statusCode());
    }
}