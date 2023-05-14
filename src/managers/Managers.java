package managers;

import managers.historymanagers.HistoryManager;
import managers.historymanagers.InMemoryHistoryManager;
import managers.taskmanagers.FileBackedTasksManager;
import managers.taskmanagers.InMemoryTaskManager;
import managers.taskmanagers.TaskManager;

public class Managers {

    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getDefaultFileBackedManager() {
        return new FileBackedTasksManager();
    }
}
