package Epic;

import Subtask.SubtaskInfo;
import Task.TaskInfo;
import Task.TaskManager;
import enums.Type;

import java.util.HashMap;

public class EpicManager {

    TaskManager taskManager = new TaskManager();


    HashMap<Integer, SubtaskInfo> subtasks = new HashMap<>();
    public HashMap<Integer, EpicInfo> epics = new HashMap<>();

    public EpicInfo newEpic() {

        return new EpicInfo("Epic1", "Description_epic1", Type.EPIC);

    }

    public EpicInfo createEpic(EpicInfo epicInfo) {

        epicInfo.setTaskId(taskManager.getNextID());
        epics.put(epicInfo.getTaskId(), epicInfo);

        return epicInfo;
    }

    public SubtaskInfo createSubtask(SubtaskInfo subtask) {

        subtask.setTaskId(taskManager.getNextID());
        subtasks.put(subtask.getTaskId(), subtask);

        EpicInfo epicInfo = epics.get(subtask.getEpicID());

        epicInfo.addSubtask(subtask);
        epicInfo.updateEpicStatus(subtasks);

        return subtask;

    }


    public SubtaskInfo newSubtask(EpicInfo epicInfo) {

        return new SubtaskInfo("Subtask1", "Subtask1", epicInfo.getTaskId());

    }

    public HashMap<Integer, SubtaskInfo> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, EpicInfo> getAllEpics() {
        if (!epics.isEmpty()) return epics;
        else return null;
    }

    public void deleteSingleEpic(int epicID) {
        EpicInfo epicInfo = epics.get(epicID);

        if (epicInfo == null)
            return;

        for (Integer id : epicInfo.getSubs()) {
            subtasks.remove(id);
        }
        epics.remove(epicID);
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    public EpicInfo getSingleEpic(int id) {
        EpicInfo singleEpic = epics.get(id);
        if (singleEpic != null) return singleEpic;
        else {
            System.out.println("Задачи с такой ID не существует");
            return null;
        }
    }

    public EpicInfo updateSingleEpic(EpicInfo epicInfo) {
        epics.put(epicInfo.getTaskId(), epicInfo);
        return epicInfo;
    }

    public SubtaskInfo getSubtask(int id) {

        SubtaskInfo subtaskInfo = subtasks.get(id);

        return subtaskInfo;
    }

    public void deleteSubtask(int id) {
        SubtaskInfo subtask = subtasks.get(id);

        if (subtask == null) return;

        EpicInfo epicInfo = epics.get(subtask.getEpicID());
        subtasks.remove(id);
        EpicInfo.subs.remove((Integer) id);     // костыль, ибо не получается передать ArrayList<Integer> subs из EpicInfo
                                                // в EpicManager и из этого в мапе значение удаляется, а в листе нет и
        epicInfo.updateEpicStatus(subtasks);    // <- в этом методе при проходке по значениям листа появляется NullPointerException
    }


    public SubtaskInfo updateSingleSubtask(SubtaskInfo subtaskInfo) {

        subtasks.put(subtaskInfo.getTaskId(), subtaskInfo);
        EpicInfo epic = epics.get(subtaskInfo.getEpicID());
        epic.updateEpicStatus(subtasks);

        return subtaskInfo;
    }

    public void printAllSubtasks() {

        if (subtasks.isEmpty())
            System.out.println("Список подзадач пуст.");

        for (int id : subtasks.keySet()) {
            SubtaskInfo value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }

    }
    public void printAllEpics() {

        if (epics.isEmpty())
            System.out.println("Список эпиков пуст.");

        for (int id : epics.keySet()) {
            EpicInfo value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }

    }
}
