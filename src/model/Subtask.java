package model;

import model.enums.Status;
import model.enums.Type;

import java.time.Instant;

public class Subtask extends Task {

    private final int epicId;

    public Subtask(String name,
                   String description,
                   int epicId,
                   int taskId,
                   Status status) {
        super(name, description, taskId, status);
        this.epicId = epicId;
        this.type = Type.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
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



}
