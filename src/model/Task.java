package model;

import model.enums.Status;
import model.enums.Type;

import java.time.Instant;

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

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
