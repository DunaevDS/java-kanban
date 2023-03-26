import Epic.EpicInfo;
import Epic.EpicManager;
import Subtask.SubtaskInfo;
import Subtask.SubtaskManager;
import Task.TaskInfo;
import Task.TaskManager;

import java.util.HashMap;


public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        EpicManager epicManager = new EpicManager();
        SubtaskManager subtaskManager = new SubtaskManager();

/*        TaskInfo task1 = taskManager.createTask(taskManager.newTask());  //тест. Создаем задачи
        TaskInfo task2 = taskManager.createTask(taskManager.newTask());
        HashMap<Integer, TaskInfo> returnedTasks = taskManager.getAllTasks();        // получаем все задачи
       // taskManager.deleteAllTasks();     // удаляем задачи
        TaskInfo singleTask = taskManager.getSingleTask(3); // получаем задачу по нужному ID
        TaskInfo updateTask = taskManager.updateSingleTask(task2); // обновляем задачу

        EpicInfo epic1 = epicManager.createEpic(epicManager.newEpic());
        HashMap<Integer, EpicInfo> returnedEpics = epicManager.getAllEpics();        // получаем все эпики
        // epicManager.deleteAllEpics();     // удаляем эпики
         EpicInfo singleEpic = epicManager.getSingleEpic(3);*/ // получаем эпик по нужному ID

         EpicInfo epic1 = epicManager.createEpic(epicManager.newEpic());
         SubtaskInfo createSubtask = subtaskManager.createSubtask(subtaskManager.newSubtask(epic1));
    }


}
