package model;

import model.enums.Status;
import model.enums.Type;
import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subs;

    public Epic(String name, String description, int id, Status status) {
        super(name, description, id, status);
        this.type = Type.EPIC;
        this.subs = new ArrayList<>();
    }

    public ArrayList<Integer> getSubs() {
        return subs;
    }

    public void addSubtask(int id) {
        subs.add(id);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}

