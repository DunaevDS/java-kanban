import managers.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;

import managers.TaskManager;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        model.Task task1 = taskManager.createTask(taskManager.newTask());                    // Создаем задачи
        model.Task task2 = taskManager.createTask(taskManager.newTask());

        var returnedTasks = taskManager.getAllTasks();            // получаем все задачи

        model.Task getSingleTask = taskManager.getSingleTask(1);                          // получаем задачу по нужному ID
        taskManager.deleteSingleTask(2);                                               // удаляем одну задачу
        model.Task updateTask = taskManager.updateSingleTask(task1);                       // обновляем задачу
        taskManager.printAllTasks();                                                    // принт задач
        taskManager.deleteAllTasks();                                                 // удаляем задачи



        /*model.Epic epic = taskManager.createEpic(taskManager.newEpic());              // создаем эпик
        model.Epic epic2 = taskManager.createEpic(taskManager.newEpic());
        model.Epic epic3 = taskManager.createEpic(taskManager.newEpic());
        var returnedEpics = taskManager.getAllEpics();        // получаем все эпики
        model.Subtask createSubtask1 = taskManager.createSubtask(taskManager.newSubtask(epic));   // создаем сабтаски
        model.Subtask createSubtask2 = taskManager.createSubtask(taskManager.newSubtask(epic));
        taskManager.printAllEpics();                                                 // принт эпиков
        taskManager.printAllSubtasks();                                              // принт подзадач
        taskManager.deleteSingleEpic(1);                                       // удаляем эпик, содержащий сабтаски
        taskManager.deleteAllEpics();*/                                                // удаляем эпики



        /*model.Epic epic = taskManager.createEpic(taskManager.newEpic());                           // создаем эпик и сабтаски для него
        model.Subtask Subtask1 = taskManager.createSubtask(taskManager.newSubtask(epic));
        model.Subtask Subtask2 = taskManager.createSubtask(taskManager.newSubtask(epic));
        Subtask1.setStatus(Status.DONE);                                                        // меняем статус задачи
        epic = taskManager.updateSingleEpic(epic);                                             // смотрим, что статус эпика меняется после обновления
        model.Subtask Subtask3 = taskManager.createSubtask(taskManager.newSubtask(epic));     // здесь статус эпика меняется
        model.Subtask getSingleSubtask = taskManager.getSingleSubtask(2);                 // получаем сабтаск
        taskManager.deleteSingleSubtask(3);                                              // удаляем сабтаск
        model.Subtask updatedSubtask = taskManager.updateSingleSubtask(Subtask1);          // обновляем сабтаск
        taskManager.deleteAllSubtasks();*/


        /*model.Epic epic = taskManager.createEpic(taskManager.newEpic());                        // создаем эпик и сабтаски для него
        model.Subtask Subtask1 = taskManager.createSubtask(taskManager.newSubtask(epic));
        model.Subtask Subtask2 = taskManager.createSubtask(taskManager.newSubtask(epic));
        Subtask1.setStatus(Status.DONE);
        epic = taskManager.updateSingleEpic(epic);
        Subtask2.setStatus(Status.DONE);
        epic = taskManager.updateSingleEpic(epic);*/                                  // статус эпика меняется на DONE


    }


}
