package model;

import model.enums.Type;
import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subs;

    public Epic(String name, String description, Type type) {
        super(name, description);
        this.type = Type.EPIC;
        this.subs = new ArrayList<>();
    }

    public ArrayList<Integer> getSubs() {
        return subs;
    }

    public void addSubtask(Subtask subtask) {
        subs.add(subtask.getTaskId());
    }


}

