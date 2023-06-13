package managers.taskmanagers;

import clients.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.Subtask;
import model.Task;
import model.utils.DataTransformation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        String jsonPrioritizedTasks = client.load("tasks");
        Type prioritizedTaskType = new TypeToken<List<Task>>() {}.getType();
        List<Task> priorityTasks = gson.fromJson(jsonPrioritizedTasks, prioritizedTaskType);
        prioritizedTasks.addAll(priorityTasks);

        String gsonHistory = client.load("tasks/history");
        Type historyType = new TypeToken<List<Task>>() {}.getType();
        List<Task> history = gson.fromJson(gsonHistory, historyType);
        getHistory().addAll(history);

        String jsonTasks = client.load("tasks/task");
        Type taskType = new TypeToken<Map<Integer,Task>>() {}.getType();
        Map<Integer,Task> taskList = gson.fromJson(jsonTasks, taskType);
        tasks.putAll(taskList);

        String jsonEpics = client.load("tasks/epic");
        Type epicType = new TypeToken<Map<Integer, Epic>>() {}.getType();
        Map<Integer,Epic> epicList = gson.fromJson(jsonEpics, epicType);
        epics.putAll(epicList);

        String jsonSubtasks = client.load("tasks/subtask");
        Type subtaskType = new TypeToken<Map<Integer, Subtask>>() {}.getType();
        Map<Integer, Subtask> subtasksList = gson.fromJson(jsonSubtasks, subtaskType);
        subtasks.putAll(subtasksList);
    }
}
