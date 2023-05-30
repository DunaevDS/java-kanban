package model;

import model.enums.Status;
import model.enums.Type;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {

    private final int epicId;

    public Subtask(int taskId,
                   String name,
                   Status status,
                   String description,
                   LocalDateTime startTime,
                   long duration,
                   int epicId) {
        super(taskId, name, status, description, startTime, duration);
        this.epicId = epicId;
    }


    public int getEpicId() {
        return epicId;
    }

    @Override
    public Type getType(){
        return Type.SUBTASK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public  String toString(){
        return  taskId
                + "," + Type.SUBTASK
                + "," + name
                + "," + status
                + ",\"" + description
                + "\"," + getStartTime()
                + "," + duration
                + "," + getEndTime()
                + "," + epicId;

    }
}
