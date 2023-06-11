package servers;

import managers.taskmanagers.TaskManager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.enums.Endpoint;

import com.google.gson.Gson;
import model.Task;
import model.Epic;
import model.Subtask;
import model.utils.DataTransformation;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.io.OutputStream;
import java.io.IOException;

import java.util.Collection;

public class HttpTaskServer {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final int PORT = 8080;
    private final TaskManager manager;
    private final HttpServer server;
    private final Gson gson;
    private String link;
    private int queryInt;
    private String query;

    public HttpTaskServer(TaskManager manager) throws IOException {
        this.manager = manager;
        server = HttpServer.create();
        server.bind(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler());
        gson = DataTransformation.createGson();
    }

    public void start() {
        server.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop() {
        server.stop(0);
        System.out.println("HTTP-сервер остановлен на " + PORT + " порту!");
    }

    class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            link = exchange.getRequestURI().getPath();
            query = exchange.getRequestURI().getQuery();
            endpointSearch(exchange);
        }

        private void endpointSearch(HttpExchange exchange) throws IOException {
            queryInt = getQueryInt(query, queryInt);
            Endpoint endpoint = getEndpoint(link, exchange.getRequestMethod(), query);

            switch (endpoint) {
                case GET_TASKS: {
                    handleGetPrioritizedTasks(exchange);
                    break;
                }
                case GET_TASKS_TASK: {
                    handleGetTasks(exchange);
                    break;
                }
                case GET_TASKS_TASK_ID: {
                    handleGetTasksById(exchange, queryInt);
                    break;
                }
                case GET_TASKS_EPIC: {
                    handleGetEpics(exchange);
                    break;
                }
                case GET_TASKS_EPIC_ID: {
                    handleGetEpicById(exchange, queryInt);
                    break;
                }
                case GET_TASKS_SUBTASK: {
                    handleGetSubtasks(exchange);
                    break;
                }
                case GET_TASKS_SUBTASK_EPIC_ID: {
                    handleGetSubtasksByID(exchange, queryInt);
                    break;
                }
                case GET_TASKS_HISTORY: {
                    handleGetHistory(exchange);
                }
                case POST_TASKS_TASK: {
                    handleCreateTask(exchange);
                    break;
                }
                case POST_TASKS_EPIC: {
                    handleCreateEpic(exchange);
                    break;
                }
                case POST_TASKS_SUBTASK: {
                    handleCreateSubtask(exchange);
                    break;
                }
                case DELETE_TASKS_TASK: {
                    handleDeleteEverything(exchange);
                    break;
                }
                case DELETE_TASKS_TASK_ID: {
                    handleDeleteTask(exchange, queryInt);
                    break;
                }
                case DELETE_TASKS_EPIC_ID: {
                    handleDeleteEpic(exchange, queryInt);
                    break;
                }
                case DELETE_TASKS_SUBTASK_ID: {
                    handleDeleteSubtask(exchange, queryInt);
                    break;
                }
                default:
                    writeResponse(exchange, "Такого эндпоинта не существует", 404);
            }
        }

        // Мне самому не нравится как в этом методе и в методе выше все нагорожено, но для 1 итерации мне главное понять
        // правильная ли логика работы. На каникулах в практикуме планирую переписать этот код используя паттерны (на данный
        // момент не изучал их, но знаю что с ними будет гораздо проще)
        private Endpoint getEndpoint(String requestPath, String requestMethod, String query) {

            String[] pathParts = requestPath.split("/");
            switch (requestMethod) {
                case "GET":
                    if (pathParts[1].equals("tasks")) {
                        if (pathParts.length == 2 && query == null) return Endpoint.GET_TASKS;
                        else if (pathParts.length == 3 && query == null) {
                            switch (pathParts[2]) {
                                case "task":
                                    return Endpoint.GET_TASKS_TASK;
                                case "epic":
                                    return Endpoint.GET_TASKS_EPIC;
                                case "history":
                                    return Endpoint.GET_TASKS_HISTORY;
                                case "subtask":
                                    return Endpoint.GET_TASKS_SUBTASK;
                            }
                        } else if (pathParts.length == 3) {
                            if (pathParts[2].equals("task"))
                                return Endpoint.GET_TASKS_TASK_ID;
                            else if (pathParts[2].equals("epic"))
                                return Endpoint.GET_TASKS_EPIC_ID;
                        } else if (pathParts.length == 4 && query != null) {
                            if (pathParts[2].equals("subtask")
                                    && pathParts[3].equals("epic"))
                                return Endpoint.GET_TASKS_SUBTASK_EPIC_ID;
                        }
                    }
                    break;
                case "POST":
                    if (pathParts[1].equals("tasks")) {
                        if (pathParts.length == 3) {
                            switch (pathParts[2]) {
                                case "task":
                                    return Endpoint.POST_TASKS_TASK;
                                case "epic":
                                    return Endpoint.POST_TASKS_EPIC;
                                case "subtask":
                                    return Endpoint.POST_TASKS_SUBTASK;
                            }
                        }
                    }
                    break;
                case "DELETE":
                    if (pathParts[1].equals("tasks")) {
                        if (pathParts.length == 3 && query == null) {
                            if (pathParts[2].equals("task")) {
                                return Endpoint.DELETE_TASKS_TASK;
                            }
                        } else if (pathParts.length == 3) {
                            switch (pathParts[2]) {
                                case "task":
                                    return Endpoint.DELETE_TASKS_TASK_ID;
                                case "epic":
                                    return Endpoint.DELETE_TASKS_EPIC_ID;
                                case "subtask":
                                    return Endpoint.DELETE_TASKS_SUBTASK_ID;
                            }
                        }
                    }
                    break;
                default:
                    return Endpoint.UNKNOWN;
            }
            return Endpoint.UNKNOWN;
        }

        private void handleGetPrioritizedTasks(HttpExchange httpExchange) throws IOException {
            System.out.println("GET: началась обработка /tasks запроса от клиента.\n");
            String prioritizedTasksToJson = gson.toJson(manager.getPrioritizedTasks());

            if (manager.getPrioritizedTasks().isEmpty()) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, prioritizedTasksToJson, 200);
            }
        }

        private void handleGetTasks(HttpExchange httpExchange) throws IOException {
            System.out.println("GET: началась обработка /tasks/task запроса от клиента.\n");
            Collection<Task> tasks = manager.getAllTasks();

            //пытался сделать проверку if tasks().isEmpty() и так, но она не отрабатывает почему то
            if (gson.toJson(tasks).equals("null")) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, gson.toJson(tasks), 200);
            }
        }

        private void handleGetEpics(HttpExchange httpExchange) throws IOException {
            System.out.println("GET: началась обработка /tasks/epic запроса от клиента.\n");
            Collection<Epic> epics = manager.getAllEpics();

            if (gson.toJson(epics).equals("null")) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);

            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, gson.toJson(epics), 200);
            }
        }

        private void handleGetSubtasks(HttpExchange httpExchange) throws IOException {
            System.out.println("GET: началась обработка /tasks/subtask запроса от клиента.\n");
            Collection<Subtask> subtasks = manager.getAllSubtasks();

            if (gson.toJson(subtasks).equals("null")) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, gson.toJson(subtasks), 200);
            }
        }

        private void handleGetTasksById(HttpExchange httpExchange, int id) throws IOException {
            System.out.println("GET: началась обработка /tasks/task/?id=" + id + " запроса от клиента.\n");
            String taskByIDToJson = gson.toJson(manager.getSingleTask(id));

            if (taskByIDToJson.equals("null")) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, taskByIDToJson, 200);
            }
            queryInt = 0; // обнуление, иначе после вызова get history значение будет не 0
        }

        private void handleGetHistory(HttpExchange httpExchange) throws IOException {
            System.out.println("GET: началась обработка /tasks/history запроса от клиента.\n");
            String historyToJson = gson.toJson(manager.getHistory());

            if (manager.getHistory().isEmpty()) {
                writeResponse(httpExchange, "Request failed", 404);
            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, historyToJson, 200);
            }
        }

        private void handleGetEpicById(HttpExchange httpExchange, int id) throws IOException {
            System.out.println("GET: началась обработка /tasks/epic/?id=" + id + " запроса от клиента.\n");
            String epicToJson = gson.toJson(manager.getSingleEpic(id));

            if (epicToJson.equals("null")) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, epicToJson, 200);
            }
            queryInt = 0; // обнуление
        }

        private void handleGetSubtasksByID(HttpExchange httpExchange, int id) throws IOException {
            System.out.println("GET: началась обработка /tasks/subtask/epic/?id=" + " запроса от клиента.\n");
            String subtaskEpicToJson = gson.toJson(manager.getAllSubtasksByEpicId(id));

            if (subtaskEpicToJson.equals("null")) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, subtaskEpicToJson, 200);
            }
            queryInt = 0; // обнуление
        }

        //стоит ли заморачиваться и делать startTime у созданной таски LocalDateTime.now ?
        private void handleCreateTask(HttpExchange httpExchange) throws IOException {
            System.out.println("POST: началась обработка /tasks/task запроса от клиента.\n");
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Task task = gson.fromJson(body, Task.class);

            if (manager.getSingleTask(task.getTaskId()) == null) {
                manager.createTask(task);
                writeResponse(httpExchange, "Задача #" + task.getTaskId() + " создана.\n" + task, 201);
            } else {
                manager.updateSingleTask(task);
                writeResponse(httpExchange, "Задача #" + task.getTaskId() + " обновлена.\n" + task, 200);
            }
        }

        private void handleCreateEpic(HttpExchange httpExchange) throws IOException {
            System.out.println("POST: началась обработка /tasks/epic запроса от клиента.\n");
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Epic epic = gson.fromJson(body, Epic.class);

            if (manager.getSingleEpic(epic.getTaskId()) == null) {
                manager.createEpic(epic);
                writeResponse(httpExchange, "Задача #" + epic.getTaskId() + " создана.\n" + body, 201);
            } else {
                manager.updateSingleEpic(epic);
                writeResponse(httpExchange, "Задача #" + epic.getTaskId() + " обновлена.\n" + body, 200);
            }
        }

        private void handleCreateSubtask(HttpExchange httpExchange) throws IOException {
            System.out.println("POST: началась обработка /tasks/subtask запроса от клиента.\n");
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Subtask subtask = gson.fromJson(body, Subtask.class);

            if (manager.getSingleSubtask(subtask.getTaskId()) == null) {
                manager.createSubtask(subtask);
                writeResponse(httpExchange, "Задача #" + subtask.getTaskId() + " создана.\n" + body, 201);
            } else {
                manager.updateSingleSubtask(subtask);
                writeResponse(httpExchange, "Задача #" + subtask.getTaskId() + " обновлена.\n" + body, 200);
            }
        }

        private void handleDeleteEverything(HttpExchange httpExchange) throws IOException {
            System.out.println("DELETE: началась обработка /tasks/task запроса от клиента.\n");
            manager.removeEverything();
            writeResponse(httpExchange, "Все таски, эпики и сабтаски удалены.", 200);
        }

        private void handleDeleteTask(HttpExchange httpExchange, int id) throws IOException {
            System.out.println("DELETE: началась обработка /tasks/task/?id=" + id + " запроса от клиента.\n");

            if (manager.getSingleTask(id) != null) {
                manager.deleteSingleTask(id);
                writeResponse(httpExchange, "Задача #" + id + " удалена.\n", 200);
            } else {
                writeResponse(httpExchange, "Задача #" + id + " не найдена.\n", 404);
            }
            queryInt = 0; // обнуление
        }

        private void handleDeleteEpic(HttpExchange httpExchange, int id) throws IOException {
            System.out.println("DELETE: началась обработка /tasks/epic/?id=" + id + " запроса от клиента.\n");
            if (manager.getSingleEpic(id) != null) {
                manager.deleteSingleEpic(id);
                System.out.println("Задача #" + id + " удалена.\n");
                writeResponse(httpExchange, "Задача #" + id + " удалена.\n", 200);
            } else {
                writeResponse(httpExchange, "Задача #" + id + " не найдена.\n", 404);
            }
            queryInt = 0; // обнуление
        }

        private void handleDeleteSubtask(HttpExchange httpExchange, int id) throws IOException {
            System.out.println("DELETE: началась обработка /tasks/subtask/?id=" + id + " запроса от клиента.\n");

            if (manager.getSingleSubtask(id) != null) {
                manager.deleteSingleSubtask(id);
                System.out.println("Задача #" + id + " удалена.\n");
                writeResponse(httpExchange, "Задача #" + id + " удалена.\n", 200);
            } else {
                writeResponse(httpExchange, "Задача #" + id + " не найдена.\n", 404);
            }
            queryInt = 0; // обнуление
        }

        private void writeResponse(HttpExchange exchange,
                                   String responseString,
                                   int responseCode) throws IOException {
            if (responseString.isBlank()) {
                exchange.sendResponseHeaders(responseCode, 0);
            } else {
                byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
                exchange.sendResponseHeaders(responseCode, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
            exchange.close();
        }
    }

    private int getQueryInt(String query, int queryInt) {
        if (query != null) {
            String[] queryParts = query.split("id=");
            queryInt = Integer.parseInt(queryParts[1]);
        }
        return queryInt;
    }
}