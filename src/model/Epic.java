package model;

import model.enums.Status;
import model.enums.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    public  ArrayList<Integer> subs;

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

