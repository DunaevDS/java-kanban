package model;

import model.enums.Status;
import model.enums.Type;

import java.time.Instant;

public class Subtask extends Task {

    private final int epicId;

    public Subtask(int taskId,
                   Type type,
                   String name,
                   Status status,
                   String description,
                   int epicId) {
        super(taskId,type,name,status, description);
        this.epicId = epicId;
        this.type = Type.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }


    @Override
    public  String toString(){
        return  taskId
                + "," + type
                + "," + name
                + "," + status
                + ",\"" + description
                + "\"," + epicId;
    }

    }
