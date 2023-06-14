package managers.taskmanagers;

import clients.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.Subtask;
import model.Task;
import model.utils.DataTransformation;

import java.lang.reflect.Type;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    protected KVTaskClient client;
    private final Gson gson;

    public HttpTaskManager() {
        gson = DataTransformation.createGson();
        client = new KVTaskClient();
    }

    @Override
    public void save() {
        String prioritizedTasks = gson.toJson(getPrioritizedTasks());
        client.put("tasks", prioritizedTasks);

        String history = gson.toJson(getHistory());
        client.put("tasks/history", history);

        String tasks = gson.toJson(getAllTasks());
        client.put("tasks/task", tasks);

        String epics = gson.toJson(getAllEpics());
        client.put("tasks/epic", epics);

        String subtasks = gson.toJson(getAllSubtasks());
        client.put("tasks/subtask", subtasks);
    }

    public void load() {
        int counterId = 0;
        String jsonPrioritizedTasks = client.load("tasks");
        Type prioritizedTaskType = new TypeToken<List<Task>>() {}.getType();
        List<Task> priorityTasks = gson.fromJson(jsonPrioritizedTasks, prioritizedTaskType);
        prioritizedTasks.addAll(priorityTasks);

        String gsonHistory = client.load("tasks/history");
        Type historyType = new TypeToken<List<Task>>() {}.getType();
        List<Task> historyList = gson.fromJson(gsonHistory, historyType);
        for (Task task : historyList) {
            historyManager.add(task);
        }

        String jsonTasks = client.load("tasks/task");
        Type taskType = new TypeToken<List<Task>>() {}.getType();
        List<Task> taskList = gson.fromJson(jsonTasks, taskType);
        for (Task task : taskList) {
            if (task.getTaskId() > counterId) {
                counterId = task.getTaskId();
            }
            tasks.put(task.getTaskId(), task);
        }

        String jsonEpics = client.load("tasks/epic");
        Type epicType = new TypeToken<List<Epic>>() {}.getType();
        List<Epic> epicList = gson.fromJson(jsonEpics, epicType);
        for (Epic epic : epicList) {
            if (epic.getTaskId() > counterId) {
                counterId = epic.getTaskId();
            }
            epics.put(epic.getTaskId(), epic);
        }

        String jsonSubtasks = client.load("tasks/subtask");
        Type subtaskType = new TypeToken<List<Subtask>>() {}.getType();
        List<Subtask> subtasksList = gson.fromJson(jsonSubtasks, subtaskType);
        for (Subtask subtask : subtasksList) {
            if (subtask.getTaskId() > counterId) {
                counterId = subtask.getTaskId();
            }
            subtasks.put(subtask.getTaskId(), subtask);
        }
        setId(counterId);
    }
}
