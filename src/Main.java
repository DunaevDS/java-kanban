import managers.TaskManager;
import managers.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager inMemoryInMemoryTaskManager = new InMemoryTaskManager();

        /*Task task1 = inMemoryInMemoryTaskManager.createTask(inMemoryInMemoryTaskManager.newTask());                    // Создаем задачи
        Task task2 = inMemoryInMemoryTaskManager.createTask(inMemoryInMemoryTaskManager.newTask());
        Task task3 = inMemoryInMemoryTaskManager.createTask(inMemoryInMemoryTaskManager.newTask());
        Task task4 = inMemoryInMemoryTaskManager.createTask(inMemoryInMemoryTaskManager.newTask());
        var returnedTasks = inMemoryInMemoryTaskManager.getAllTasks();            // получаем все задачи

        Task getSingleTask = inMemoryInMemoryTaskManager.getSingleTask(1);                          // получаем задачу по нужному ID
        inMemoryInMemoryTaskManager.deleteSingleTask(2);                                               // удаляем одну задачу
        Task updateTask = inMemoryInMemoryTaskManager.updateSingleTask(task1);                       // обновляем задачу
        inMemoryInMemoryTaskManager.printAllTasks();                                                    // принт задач
        inMemoryInMemoryTaskManager.deleteAllTasks();*/                                                // удаляем задачи



        /*Epic epic = inMemoryInMemoryTaskManager.createEpic(inMemoryInMemoryTaskManager.newEpic());              // создаем эпик
        Epic epic2 = inMemoryInMemoryTaskManager.createEpic(inMemoryInMemoryTaskManager.newEpic());
        Epic epic3 = inMemoryInMemoryTaskManager.createEpic(inMemoryInMemoryTaskManager.newEpic());
        var returnedEpics = inMemoryInMemoryTaskManager.getAllEpics();        // получаем все эпики
        Subtask createSubtask1 = inMemoryInMemoryTaskManager.createSubtask(inMemoryInMemoryTaskManager.newSubtask(epic));   // создаем сабтаски
        Subtask createSubtask2 = inMemoryInMemoryTaskManager.createSubtask(inMemoryInMemoryTaskManager.newSubtask(epic));
        inMemoryInMemoryTaskManager.printAllEpics();                                                 // принт эпиков
        inMemoryInMemoryTaskManager.printAllSubtasks();                                              // принт подзадач
        inMemoryInMemoryTaskManager.deleteSingleEpic(1);                                       // удаляем эпик, содержащий сабтаски
        inMemoryInMemoryTaskManager.deleteAllEpics();*/                                               // удаляем эпики



        /*Epic epic = inMemoryInMemoryTaskManager.createEpic(inMemoryInMemoryTaskManager.newEpic());                           // создаем эпик и сабтаски для него
        Subtask Subtask1 = inMemoryInMemoryTaskManager.createSubtask(inMemoryInMemoryTaskManager.newSubtask(epic));
        Subtask Subtask2 = inMemoryInMemoryTaskManager.createSubtask(inMemoryInMemoryTaskManager.newSubtask(epic));
        Subtask1.setStatus(Status.DONE);                                                        // меняем статус задачи
        epic = inMemoryInMemoryTaskManager.updateSingleEpic(epic);                                             // смотрим, что статус эпика меняется после обновления
        Subtask Subtask3 = inMemoryInMemoryTaskManager.createSubtask(inMemoryInMemoryTaskManager.newSubtask(epic));     // здесь статус эпика меняется
        Subtask getSingleSubtask = inMemoryInMemoryTaskManager.getSingleSubtask(2);                 // получаем сабтаск
        InMemoryTaskManager.deleteSingleSubtask(3);                                              // удаляем сабтаск
        Subtask updatedSubtask = inMemoryInMemoryTaskManager.updateSingleSubtask(Subtask1);          // обновляем сабтаск
        inMemoryInMemoryTaskManager.deleteAllSubtasks();*/


        /*Epic epic = inMemoryInMemoryTaskManager.createEpic(inMemoryInMemoryTaskManager.newEpic());                        // создаем эпик и сабтаски для него
        Subtask Subtask1 = inMemoryInMemoryTaskManager.createSubtask(inMemoryInMemoryTaskManager.newSubtask(epic));
        Subtask Subtask2 = inMemoryInMemoryTaskManager.createSubtask(inMemoryInMemoryTaskManager.newSubtask(epic));
        Subtask1.setStatus(Status.DONE);
        epic = inMemoryInMemoryTaskManager.updateSingleEpic(epic);
        Subtask2.setStatus(Status.DONE);
        epic = inMemoryInMemoryTaskManager.updateSingleEpic(epic);*/                                  // статус эпика меняется на DONE


        /*Epic epic3 = inMemoryInMemoryTaskManager.createEpic(inMemoryInMemoryTaskManager.newEpic());
        Subtask Subtask3 = inMemoryInMemoryTaskManager.createSubtask(inMemoryInMemoryTaskManager.newSubtask(epic2));
        Subtask Subtask4 = inMemoryInMemoryTaskManager.createSubtask(inMemoryInMemoryTaskManager.newSubtask(epic2));
        Subtask updatedSubtask = inMemoryInMemoryTaskManager.updateSingleSubtask(Subtask1);   // обновили сабтаск
        var alltasks = inMemoryInMemoryTaskManager.getAllTasks();
        var allEpics = inMemoryInMemoryTaskManager.getAllEpics();
        var allsubs = inMemoryInMemoryTaskManager.getAllSubtasks();*/


    }


}
