package model;

import model.enums.Status;
import model.enums.Type;

public class Subtask extends Task {

    private final int epicID;

    public Subtask(String name, String description, int epicId, int taskId, Status status) {
        super(name, description, taskId, status);
        this.epicID = epicId;
        this.type = Type.SUBTASK;
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }

// вы отметили что у меня отсутствуют методы по удалению 1 сабтаски и всех сабтасок...но они у меня в TaskManager
    // на строках 83 и 103 под названиями deleteSingleSubtask и deleteAllSubtask

}
