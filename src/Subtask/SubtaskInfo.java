package Subtask;

import Task.TaskInfo;
import enums.Type;

public class SubtaskInfo extends TaskInfo {


    int epicID;

    public SubtaskInfo(String name, String description, int epicID) {
        super(name, description);
        this.epicID = epicID;
        this.type = Type.SUBTASK;
    }

    public int getEpicID() {
        return epicID;
    }
}
