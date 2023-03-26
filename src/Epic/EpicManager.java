package Epic;
import Epic.EpicInfo;
import Subtask.SubtaskInfo;
import Task.TaskInfo;
import Task.TaskManager;
import enums.Status;
import enums.Type;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class EpicManager {

    TaskManager taskManager = new TaskManager();
    TaskInfo taskInfo = new TaskInfo();



    public HashMap<Integer, EpicInfo> epics = new HashMap<>();

    public HashMap<Integer, EpicInfo> getEpics() {
        return epics;
    }


    public EpicInfo newEpic() {

        return new EpicInfo("Epic1", "Description_epic1", Type.EPIC);

    }

    public EpicInfo createEpic(EpicInfo epicInfo) {

        epicInfo.setTaskId(taskManager.getNextID());
        epics.put(epicInfo.getTaskId(), epicInfo);

        return epicInfo;
    }

    public HashMap<Integer, EpicInfo> getAllEpics() {

        if (!epics.isEmpty()) return epics;
         else return null;
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public EpicInfo getSingleEpic(int id) {
        EpicInfo singleEpic = epics.get(id);
        if (singleEpic != null) return singleEpic;
        else {System.out.println("Задачи с такой ID не существует");
            return null;}
    }

    public TaskInfo updateSingleTask (EpicInfo epicInfo) {
        epics.put(epicInfo.getTaskId(), epicInfo);
        return epicInfo;
    }




}
