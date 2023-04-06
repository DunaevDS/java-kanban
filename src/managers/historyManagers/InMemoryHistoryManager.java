package managers.historyManagers;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        sizeCheck(task);
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    private void sizeCheck(Task task) {
        if (history.size() >= 10) {
            history.remove(0);
        }
    }
}
