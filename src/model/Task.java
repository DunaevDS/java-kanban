package model;

import model.enums.Status;
import model.enums.Type;

import java.time.Instant;
import java.time.LocalDateTime;

public class Task {
    protected int taskId;
    protected String name;
    protected String description;
    protected Status status;
    protected long duration;
    protected LocalDateTime startTime;

    public Task(int taskId,
                String name,
                Status status,
                String description,
                LocalDateTime startTime,
                long duration

    ) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.taskId = taskId;
        this.startTime = startTime;
        this.duration = duration;
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
        return Type.TASK;
    }

    public int getEpicId() {
        return -1;
    }

    public LocalDateTime getStartTime() {

        return startTime;

    }
    public LocalDateTime getEndTime() {

        final int SECONDS_IN_ONE_MINUTE = 60;

        return startTime.plusSeconds(duration * SECONDS_IN_ONE_MINUTE);

    }


    @Override
    public String toString() {
        return taskId
                + "," + Type.TASK
                + "," + name
                + "," + status
                + ",\"" + description
                + "\"" + getStartTime()
                + "," + duration
                + "," + getEndTime();
    }
}
