package model;

import model.enums.Status;
import model.enums.Type;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId
                && duration == task.duration
                && Objects.equals(name, task.name)
                && Objects.equals(description, task.description)
                && status == task.status
                && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                taskId,
                name,
                description,
                status,
                duration,
                startTime
        );
    }

    @Override
    public String toString() {
        return taskId
                + "," + Type.TASK
                + "," + name
                + "," + status
                + ",\"" + description
                + "\"," + getStartTime()
                + "," + duration
                + "," + getEndTime();
    }
}
