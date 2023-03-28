package model;

import model.enums.Type;

public class Subtask extends Task {

    private final int epicID;

    public Subtask(String name, String description, int epicID) {
        super(name, description);
        this.epicID = epicID;
        this.type = Type.SUBTASK;
    }

    public int getEpicID() {
        return epicID;
    }


}
