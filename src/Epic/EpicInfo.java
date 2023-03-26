package Epic;

import Subtask.SubtaskInfo;
import Task.TaskInfo;
import enums.Status;
import enums.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class EpicInfo extends TaskInfo {

    public ArrayList<Integer> subtasks;

    public EpicInfo(String name, String description, Type type) {
        super(name, description);
        this.type = Type.EPIC;
        this.subtasks = new ArrayList<>();
    }


    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(SubtaskInfo subtask) {
        subtasks.add(subtask.getTaskId());
    }

    public void updateEpicStatus(HashMap<Integer, SubtaskInfo> subtasks) { // изначально сделал этот метод
                                                                           // в EpicManager, потом решил перенести сюда.
        int counterNew = 0;
        int counterDone = 0;

        for (var id : getSubtasks()) {
            SubtaskInfo subtask = subtasks.get(id);
            if (subtask.getStatus() == Status.NEW)
                counterNew += 1;
            if (subtask.getStatus() == Status.DONE)
                counterDone += 1;
        }
        if (getSubtasks().size() == counterNew) {
            setStatus(Status.NEW);
            return;
        } else if (getSubtasks().size() == counterDone) {
            setStatus(Status.DONE);
            return;
        }
        setStatus(Status.IN_PROGRESS);

    }

}

