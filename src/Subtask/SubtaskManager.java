package Subtask;

import Epic.EpicInfo;
import Epic.EpicManager;
import Task.TaskManager;

import java.util.HashMap;

public class SubtaskManager {

    TaskManager taskManager = new TaskManager();
    HashMap<Integer, SubtaskInfo> subtasks = new HashMap<>();
    EpicManager epicManager = new EpicManager();
    HashMap<Integer, EpicInfo> epics = epicManager.getEpics();




   public HashMap<Integer, SubtaskInfo> getSubtasks() {
        return subtasks;
    }

    public SubtaskInfo newSubtask(EpicInfo epicInfo) {

        return new SubtaskInfo("Subtask1", "Subtask1", epicInfo.getTaskId());

    }

    public SubtaskInfo createSubtask(SubtaskInfo subtask) {
        EpicInfo epic1 = epicManager.createEpic(epicManager.newEpic());

        subtask.setTaskId(taskManager.getNextID());
        subtasks.put(subtask.getTaskId(), subtask);

        //EpicInfo epicInfo = epics.get(subtask.getEpicID());

        epic1.addSubtask(subtask);
        epic1.updateEpicStatus(subtasks);

        return subtask;

    }
}
