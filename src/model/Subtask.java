package model;

import model.enums.Status;
import model.enums.Type;

import java.time.Instant;

public class Subtask extends Task {

    private final int epicId;

    public Subtask(int taskId,
                   String name,
                   Status status,
                   String description,
                   int epicId) {
        super(taskId,name,status, description);
        this.epicId = epicId;
    }

    @Override
    public int getEpicId() {
        return epicId;
    }

    @Override
    public Type getType(){
        return Type.SUBTASK;
    }

    @Override
    public  String toString(){
        return  taskId
                + "," + Type.SUBTASK
                + "," + name
                + "," + status
                + ",\"" + description
                + "\"," + epicId;
    }
}
