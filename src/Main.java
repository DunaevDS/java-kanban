import Epic.EpicInfo;
import Epic.EpicManager;
import Subtask.SubtaskInfo;
import Task.TaskInfo;
import Task.TaskManager;
import enums.Status;

import java.util.HashMap;


public class Main {

    public static void main(String[] args) {            // Добрый день, ниже тесты. В SubtaskManager к Вам расписал вопрос.
                                                        // На данном ревью хочу получить обратную связь по дальнейшему пути, код приберу ко второму ревью.
        /*TaskManager taskManager = new TaskManager();  // Ожидаю миллион ошибок :)
        EpicManager epicManager = new EpicManager();

        TaskInfo task1 = taskManager.createTask(taskManager.newTask());                    // Создаем задачи
        TaskInfo task2 = taskManager.createTask(taskManager.newTask());

        HashMap<Integer, TaskInfo> returnedTasks = taskManager.getAllTasks();            // получаем все задачи

        TaskInfo getSingleTask = taskManager.getSingleTask(1);                          // получаем задачу по нужному ID
        taskManager.deleteSingleTask(2);                                               // удаляем одну задачу
        TaskInfo updateTask = taskManager.updateSingleTask(task1);                       // обновляем задачу
        taskManager.deleteAllTasks();*/                                                 // удаляем задачи



        /*EpicInfo epic1 = epicManager.createEpic(epicManager.newEpic());              // создаем эпик
        HashMap<Integer, EpicInfo> returnedEpics = epicManager.getAllEpics();        // получаем все эпики
        EpicInfo singleEpic = epicManager.getSingleEpic(3);                          // получаем эпик
        SubtaskInfo createSubtask1 = epicManager.createSubtask(epicManager.newSubtask(epic1));   // создаем сабтаск
        SubtaskInfo createSubtask2 = epicManager.createSubtask(epicManager.newSubtask(epic1));
        epicManager.deleteSingleEpic(3);
        epicManager.deleteAllEpics();*/                                                // удаляем эпики




        /*EpicInfo epic2 = epicManager.createEpic(epicManager.newEpic());                               // создаем эпик и сабтаски
        SubtaskInfo createSubtask3 = epicManager.createSubtask(epicManager.newSubtask(epic2));
        SubtaskInfo createSubtask4 = epicManager.createSubtask(epicManager.newSubtask(epic2));
        SubtaskInfo getSingleSubtask = epicManager.getSubtask(2);                              // получаем сабтаск
        EpicInfo updatedEpic1 = epicManager.updateSingleEpic(epic2);
        epicManager.deleteSubtask(6);                                                         // удаляем сабтаск
        epicManager.deleteAllSubtasks();*/



        /*EpicInfo epic3 = epicManager.createEpic(epicManager.newEpic());                            // создаем эпик и сабтаски для него
        SubtaskInfo createSubtask21 = epicManager.createSubtask(epicManager.newSubtask(epic3));
        SubtaskInfo createSubtask20 = epicManager.createSubtask(epicManager.newSubtask(epic3));
        createSubtask20.setStatus(Status.DONE);
        EpicInfo updatedEpic2 = epicManager.updateSingleEpic(epic3);                                 // должен ли статус эпика меняться на данном шаге??
        SubtaskInfo createSubtask30 = epicManager.createSubtask(epicManager.newSubtask(epic3));     // здесь статус эпика меняется
        SubtaskInfo getSingleSubtask2 = epicManager.getSubtask(30);                             // получаем сабтаск
        epicManager.deleteSubtask(20);                                                         // удаляем сабтаск
        SubtaskInfo updatedSubtask = epicManager.updateSingleSubtask(createSubtask2);             // обновляем сабтаск
        epicManager.deleteAllSubtasks();*/



    }


}
