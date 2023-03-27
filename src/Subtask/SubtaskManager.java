package Subtask;

import Epic.EpicInfo;
import Epic.EpicManager;
import Task.TaskManager;

import java.util.HashMap;

public class SubtaskManager {                                       //Изначально я планировал делать отдельный менеджер
                                                                   // под сабтаски..но у меня это не получилось сделать
/*    TaskManager taskManager = new TaskManager();                 // из-за того, что не получается передать в этот класс
    HashMap<Integer, SubtaskInfo> subtasks = new HashMap<>();      // мапу с эпиками.
    EpicManager epicManager = new EpicManager();                 // <-- как понимаю связано с тем, что на этой строке создаю новый экземпляр
    HashMap<Integer, EpicInfo> epics = epicManager.getEpics();    // ЭпикМенеджера. Подскажите, как мне в итоге быть?
                                                                  // Стоит ли мне отказаться от идеи разбить на 3 менеджера
                                                                  // и использовать только один или 2 как сейчас..или же дайте подсказку
   public HashMap<Integer, SubtaskInfo> getSubtasks() {           // как передать в этот менеджер всю информацию.
        return subtasks;                                          // К следующему ревью код приберу. Сейчас прошу задать мне правильный путь :)
    }

    public SubtaskInfo newSubtask(EpicInfo epicInfo) {

        return new SubtaskInfo("Subtask1", "Subtask1", epicInfo.getTaskId());

    }

   public SubtaskInfo createSubtask(SubtaskInfo subtask) {
        //EpicInfo epic1 = epicManager.createEpic(epicManager.newEpic());

        subtask.setTaskId(taskManager.getNextID());
        subtasks.put(subtask.getTaskId(), subtask);

        EpicInfo epicInfo = epics.get(subtask.getEpicID());

        epicInfo.addSubtask(subtask);                           // здесь вылазит NullPointerException из-за пустой мапы.
        epicInfo.updateEpicStatus(subtasks);                    // если же создать epic1 в 28 строке, то epic1.add работает
                                                                // делал попытку через геттер вытянуть из Мейна мапу, не получилось.
        return subtask;

    }*/
}
