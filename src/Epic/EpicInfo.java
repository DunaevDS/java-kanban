package Epic;

import Subtask.SubtaskInfo;
import Task.TaskInfo;
import enums.Status;
import enums.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class EpicInfo extends TaskInfo {

    public static ArrayList<Integer> subs;

    public EpicInfo(String name, String description, Type type) {
        super(name, description);
        this.type = Type.EPIC;
        subs = new ArrayList<>();
    }

    public ArrayList<Integer> getSubs() {
        return subs;
    }

    public void addSubtask(SubtaskInfo subtask) {
        subs.add(subtask.getTaskId());
    }

    public void updateEpicStatus(HashMap<Integer, SubtaskInfo> subtasks) {

        int counterNew = 0;
        int counterDone = 0;


        for (int id : getSubs()) {

            SubtaskInfo subtask = subtasks.get(id);

            if (subtask.getStatus() == Status.NEW)
                counterNew += 1;
            if (subtask.getStatus() == Status.DONE)
                counterDone += 1;
        }
        if (getSubs().size() == counterNew) {
            setStatus(Status.NEW);
            return;
        } else if (getSubs().size() == counterDone) {
            setStatus(Status.DONE);
            return;
        }
        setStatus(Status.IN_PROGRESS);

    }
}

