package model;

import model.enums.Status;
import model.enums.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private final ArrayList<Integer> subs;
    private LocalDateTime endTime;

    public Epic(int id,
                String name,
                Status status,
                String description,
                LocalDateTime startTime,
                long duration
                ) {
        super(id, name, status, description, startTime, duration);
        this.subs = new ArrayList<>();
        this.endTime = startTime;
    }

    public List<Integer> getSubs() {
        return subs;
    }

    public void addSubtask(int id) {
        subs.add(id);
    }

    public void removeSubtask(int id) {
        subs.remove(id);
    }

    public void removeAllSubtasks() {
        subs.clear();
    }

    @Override
    public Type getType(){
        return Type.EPIC;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subs, epic.subs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subs);
    }

    @Override
    public String toString() {
        return taskId
                + "," + Type.EPIC
                + "," + name
                + "," + status
                + ",\"" + description
                + "\"," + getStartTime()
                + "," + duration
                + "," + getEndTime();
    }
}

