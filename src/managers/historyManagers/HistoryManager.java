package managers.historyManagers;

import model.Task;

import java.util.List;

public interface HistoryManager {

    // добавление задачи
    void add(Task task);

    // получение истории
     List<Task> getHistory();
}
