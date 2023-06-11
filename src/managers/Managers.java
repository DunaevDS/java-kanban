package managers;

import managers.historymanagers.HistoryManager;
import managers.historymanagers.InMemoryHistoryManager;
import managers.taskmanagers.FileBackedTasksManager;
import managers.taskmanagers.HttpTaskManager;
import managers.taskmanagers.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new HttpTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getDefaultFileBackedManager() {
        return new FileBackedTasksManager();
    }
}
