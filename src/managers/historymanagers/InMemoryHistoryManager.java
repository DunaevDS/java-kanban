package managers.historymanagers;

import model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList historyManager;
    protected final Map<Integer, Node<Task>> history;

    public InMemoryHistoryManager() {
        historyManager = new CustomLinkedList();
        history = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        Node<Task> node = historyManager.linkLast(task);
        historyManager.removeNode(history.get(task.getTaskId()));
        history.put(task.getTaskId(), node);
    }

    @Override
    public void remove(int id) {
        historyManager.removeNode(history.remove(id));
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getTasks();
    }

    @Override
    public void clear(){
        history.clear();
        historyManager.clear();
    }
}
