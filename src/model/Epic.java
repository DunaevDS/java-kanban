package model;

import model.enums.Status;
import model.enums.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subs;
    private LocalDateTime endTime;

    public Epic(int id,
                String name,
                Status status,
                String description,
                LocalDateTime startTime,
                long duration) {
        super(id, name, status, description, startTime, duration);
        this.subs = new ArrayList<>();
        this.endTime = getEndTime();
    }

    public ArrayList<Integer> getSubs() {
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

    public void updateSubtask(int id) {
        subs.set(subs.indexOf(id), id);
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
    public String toString() {
        return taskId
                + "," + Type.EPIC
                + "," + name
                + "," + status
                + ",\"" + description
                + "\"";
    }
}

