package model;

import model.enums.Status;
import model.enums.Type;

public class Task {
    protected int taskId;
    protected String name;
    protected String description;
    protected Status status;
    protected Type type;

    public Task(String name,
                String description,
                int taskId,
                Status status) {
        this.name = name;
        this.description = description;
        this.type = Type.TASK;
        this.status = status;
        this.taskId = taskId;
    }

    // конструктор для преобразования из строки
    public Task(int id,
                String type,
                String name,
                Status status,
                String description) {
        this.taskId = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.type = Type.TASK;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /*@Override
    public String toString() {
        return String.format(
                "Task { taskId: %s, name: %s, description: %s, status: %s, type: %s }",
                taskId, name, description, status, type);
    }*/

    /*@Override
    public String toString() {
        return "Task {"
                + "id: " + taskId
                + ", title: '" + type + '\''
                + ", description: '" + description + '\''
                + ", status: " + status
                + '}';
    }*/
    @Override
    public  String toString(){
        return  taskId
                + ", " + type
                + ", " + name
                + ", " + status
                + ", " + description
                + ",";
    }
}
