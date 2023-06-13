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
        Type prioritizedTaskType = new TypeToken<Collection<Task>>() {}.getType();
        Collection<Task> priorityTasks = gson.fromJson(jsonPrioritizedTasks, prioritizedTaskType);
        getPrioritizedTasks().addAll(priorityTasks);

        String gsonHistory = client.load("tasks/history");
        Type historyType = new TypeToken<List<Task>>() {}.getType();
        List<Task> history = gson.fromJson(gsonHistory, historyType);
        getHistory().addAll(history);

        String jsonTasks = client.load("tasks/task");
        Type taskType = new TypeToken<Collection<Task>>() {}.getType();
        Collection<Task> taskList = gson.fromJson(jsonTasks, taskType);
        Collection<Task> tasks = getAllTasks();
        try {
            tasks.addAll(taskList);
        } catch (NullPointerException e) {
            tasks = new ArrayList<Task>(taskList);
        }

        String jsonEpics = client.load("tasks/epic");
        Type epicType = new TypeToken<Collection<Epic>>() {}.getType();
        Collection<Epic> epicList = gson.fromJson(jsonEpics, epicType);
        Collection<Epic> epics = getAllEpics();
        try {
            epics.addAll(epicList);
        } catch (NullPointerException e) {
            epics = new ArrayList<Epic>(epicList);
        }

        String jsonSubtasks = client.load("tasks/subtask");
        Type subtaskType = new TypeToken<Collection<Subtask>>() {}.getType();
        Collection<Subtask> subtasksList = gson.fromJson(jsonSubtasks, subtaskType);
        Collection<Subtask> subtasks = getAllSubtasks();
        try {
            subtasks.addAll(subtasksList);
        } catch (NullPointerException e) {
            subtasks = new ArrayList<Subtask>(subtasksList);
        }
    }
}
