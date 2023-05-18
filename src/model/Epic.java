package model;

import model.enums.Status;
import model.enums.Type;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subs;

    public Epic(int id,
                String name,
                Status status,
                String description) {
        super(id, name, status, description);
        this.subs = new ArrayList<>();
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

