package managers.historymanagers;

import model.Task;

import java.util.List;

public interface HistoryManager {

    // добавление задачи
    void add(Task task);

    //удаление задачи
    void remove(int id);

    // получение истории
    List<Task> getHistory();

    // полная очистка истории
    void clear();
}
