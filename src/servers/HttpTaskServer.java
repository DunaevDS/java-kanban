package servers;

import managers.taskmanagers.TaskManager;

import com.sun.net.httpserver.HttpExchange;
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

    public HttpTaskServer(TaskManager manager) throws IOException {
        this.manager = manager;
        server = HttpServer.create();
        server.bind(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", this::endpointSearch);
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

        private void endpointSearch(HttpExchange exchange) throws IOException {
            String link = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();
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
                    handleGetTasksById(exchange);
                    break;
                }
                case GET_TASKS_EPIC: {
                    handleGetEpics(exchange);
                    break;
                }
                case GET_TASKS_EPIC_ID: {
                    handleGetEpicById(exchange);
                    break;
                }
                case GET_TASKS_SUBTASK: {
                    handleGetSubtasks(exchange);
                    break;
                }
                case GET_TASKS_SUBTASK_EPIC_ID: {
                    handleGetSubtasksById(exchange);
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
                    handleDeleteTask(exchange);
                    break;
                }
                case DELETE_TASKS_EPIC_ID: {
                    handleDeleteEpic(exchange);
                    break;
                }
                case DELETE_TASKS_SUBTASK_ID: {
                    handleDeleteSubtask(exchange);
                    break;
                }
                default:
                    writeResponse(exchange, "Такого эндпоинта не существует", 404);
            }
        }

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

            // ну да, проблема была, естествено, в getAllTasks return null, но насколько помню добавлял return null в каком-то старом
            // ТЗ по Вашей просьбе, вот решил его и не убирать. Лучше, конечно, без него.
            if (tasks.isEmpty()) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, gson.toJson(tasks), 200);
            }
        }

        private void handleGetEpics(HttpExchange httpExchange) throws IOException {
            System.out.println("GET: началась обработка /tasks/epic запроса от клиента.\n");
            Collection<Epic> epics = manager.getAllEpics();

            if (epics.isEmpty()) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);

            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, gson.toJson(epics), 200);
            }
        }

        private void handleGetSubtasks(HttpExchange httpExchange) throws IOException {
            System.out.println("GET: началась обработка /tasks/subtask запроса от клиента.\n");
            Collection<Subtask> subtasks = manager.getAllSubtasks();

            if (subtasks.isEmpty()) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, gson.toJson(subtasks), 200);
            }
        }

        private void handleGetTasksById(HttpExchange httpExchange) throws IOException {
            String query = httpExchange.getRequestURI().getQuery();
            int id = getQueryInt(query);
            System.out.println("GET: началась обработка /tasks/task/?id=" + id + " запроса от клиента.\n");
            if (manager.getSingleTask(id) == null){
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {

                String taskByIDToJson = gson.toJson(manager.getSingleTask(id));
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, taskByIDToJson, 200);
            }
        }

        private void handleGetHistory(HttpExchange httpExchange) throws IOException {
            System.out.println("GET: началась обработка /tasks/history запроса от клиента.\n");
            if (!manager.getHistory().isEmpty()) {
                String historyToJson = gson.toJson(manager.getHistory());
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, historyToJson, 200);
            } else {
                writeResponse(httpExchange, "Request failed", 404);
            }
        }

        private void handleGetEpicById(HttpExchange httpExchange) throws IOException {
            String query = httpExchange.getRequestURI().getQuery();
            int id = getQueryInt(query);
            System.out.println("GET: началась обработка /tasks/epic/?id=" + id + " запроса от клиента.\n");
            if (manager.getSingleEpic(id) == null) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                String epicToJson = gson.toJson(manager.getSingleEpic(id));
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, epicToJson, 200);
            }
        }

        private void handleGetSubtasksById(HttpExchange httpExchange) throws IOException {
            String query = httpExchange.getRequestURI().getQuery();
            int id = getQueryInt(query);
            System.out.println("GET: началась обработка /tasks/subtask/epic/?id=" +id+ " запроса от клиента.\n");
            if (manager.getSingleSubtask(id) == null) {
                writeResponse(httpExchange, "Request failed. Resource not found", 404);
            } else {
                String subtaskEpicToJson = gson.toJson(manager.getAllSubtasksByEpicId(id));
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                writeResponse(httpExchange, subtaskEpicToJson, 200);
            }
        }

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

        private void handleDeleteTask(HttpExchange httpExchange) throws IOException {
            String query = httpExchange.getRequestURI().getQuery();
            int id = getQueryInt(query);
            System.out.println("DELETE: началась обработка /tasks/task/?id=" + id + " запроса от клиента.\n");

            if (manager.getSingleTask(id) != null) {
                manager.deleteSingleTask(id);
                writeResponse(httpExchange, "Задача #" + id + " удалена.\n", 200);
            } else {
                writeResponse(httpExchange, "Задача #" + id + " не найдена.\n", 404);
            }
        }

        private void handleDeleteEpic(HttpExchange httpExchange) throws IOException {
            String query = httpExchange.getRequestURI().getQuery();
            int id = getQueryInt(query);
            System.out.println("DELETE: началась обработка /tasks/epic/?id=" + id + " запроса от клиента.\n");
            if (manager.getSingleEpic(id) != null) {
                manager.deleteSingleEpic(id);
                System.out.println("Задача #" + id + " удалена.\n");
                writeResponse(httpExchange, "Задача #" + id + " удалена.\n", 200);
            } else {
                writeResponse(httpExchange, "Задача #" + id + " не найдена.\n", 404);
            }
        }

        private void handleDeleteSubtask(HttpExchange httpExchange) throws IOException {
            String query = httpExchange.getRequestURI().getQuery();
            int id = getQueryInt(query);
            System.out.println("DELETE: началась обработка /tasks/subtask/?id=" + id + " запроса от клиента.\n");

            if (manager.getSingleSubtask(id) != null) {
                manager.deleteSingleSubtask(id);
                System.out.println("Задача #" + id + " удалена.\n");
                writeResponse(httpExchange, "Задача #" + id + " удалена.\n", 200);
            } else {
                writeResponse(httpExchange, "Задача #" + id + " не найдена.\n", 404);
            }
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


    private int getQueryInt(String query) {
        int queryInt = 0;
        if (query != null) {
            String[] queryParts = query.split("id=");
            queryInt = Integer.parseInt(queryParts[1]);
        }
        return queryInt;
    }
}