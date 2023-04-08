package managers;

import managers.historymanagers.HistoryManager;
import managers.historymanagers.InMemoryHistoryManager;
import managers.taskmanagers.InMemoryTaskManager;
import managers.taskmanagers.TaskManager;

public class Managers {

    public TaskManager getDefault() {           // пока что не понимаю зачем этот метод делается
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
