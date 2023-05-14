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

    // конструктор для преобразования из строки
    public Subtask(int id,
                   String name,
                   Status status,
                   String description,
                   int epicID) {
        super(name, description, id, status);
        this.type = Type.SUBTASK;
        this.status = status;
        this.epicId = epicID;
        this.taskId = id;
    }

    public int getEpicId() {
        return epicId;
    }

    /*@Override
    public String toString() {
        return String.format(
                "SubTask { taskId: %s, name: %s, description: %s, status: %s, type: %s }",
                taskId, name, description, status, type);
    }*/
    @Override
    public  String toString(){
        return  taskId
                + ", " + type
                + ", " + name
                + ", " + status
                + ", " + description
                + ", " + epicId;
    }

    }
