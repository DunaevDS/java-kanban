import managers.taskManagers.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task1 = manager.createTask(manager.newTask());
        Epic epic2 = manager.createEpic(manager.newEpic());
        Subtask Subtask3 = manager.createSubtask(manager.newSubtask(epic2));
        var returnedTasks = manager.getAllTasks();

        var getSingleTask = manager.getSingleTask(1);
        var getEpic = manager.getSingleEpic(2);
        var getSub = manager.getSingleSubtask(3);
        manager.getSingleTask(1);                                   // проверяем что только 10 задач отображаются
        manager.getSingleTask(1);
        manager.getSingleTask(1);
        manager.getSingleTask(1);
        var history1 = manager.getHistory();
        manager.getSingleTask(1);
        manager.getSingleTask(1);
        manager.getSingleTask(1);
        manager.getSingleTask(1);
        manager.getSingleTask(1);
        manager.getSingleTask(1);
        manager.getSingleTask(1);


        /*manager.deleteSingleTask(2);
        Task updateTask = manager.updateSingleTask(task1);
        manager.printAllTasks();
        manager.deleteAllTasks();*/



        /*Epic epic = manager.createEpic(manager.newEpic());              // создаем эпик
        Epic epic2 = manager.createEpic(manager.newEpic());
        Epic epic3 = manager.createEpic(manager.newEpic());
        var returnedEpics = manager.getAllEpics();        // получаем все эпики
        Subtask createSubtask1 = manager.createSubtask(manager.newSubtask(epic));   // создаем сабтаски
        Subtask createSubtask2 = manager.createSubtask(manager.newSubtask(epic));
        manager.printAllEpics();                                                 // принт эпиков
        manager.printAllSubtasks();                                              // принт подзадач
        manager.deleteSingleEpic(1);                                       // удаляем эпик, содержащий сабтаски
        manager.deleteAllEpics();*/                                               // удаляем эпики



        /*Epic epic = manager.createEpic(manager.newEpic());                           // создаем эпик и сабтаски для него
        Subtask Subtask1 = manager.createSubtask(manager.newSubtask(epic));
        Subtask Subtask2 = manager.createSubtask(manager.newSubtask(epic));
        Subtask1.setStatus(Status.DONE);                                                        // меняем статус задачи
        epic = manager.updateSingleEpic(epic);                                             // смотрим, что статус эпика меняется после обновления
        Subtask Subtask3 = manager.createSubtask(manager.newSubtask(epic));     // здесь статус эпика меняется
        Subtask getSingleSubtask = manager.getSingleSubtask(2);                 // получаем сабтаск
        InMemoryTaskManager.deleteSingleSubtask(3);                                              // удаляем сабтаск
        Subtask updatedSubtask = manager.updateSingleSubtask(Subtask1);          // обновляем сабтаск
        manager.deleteAllSubtasks();*/


        /*Epic epic = manager.createEpic(manager.newEpic());                        // создаем эпик и сабтаски для него
        Subtask Subtask1 = manager.createSubtask(manager.newSubtask(epic));
        Subtask Subtask2 = manager.createSubtask(manager.newSubtask(epic));
        Subtask1.setStatus(Status.DONE);
        epic = manager.updateSingleEpic(epic);
        Subtask2.setStatus(Status.DONE);
        epic = manager.updateSingleEpic(epic);*/                                  // статус эпика меняется на DONE


        /*Epic epic3 = manager.createEpic(manager.newEpic());
        Subtask Subtask3 = manager.createSubtask(manager.newSubtask(epic2));
        Subtask Subtask4 = manager.createSubtask(manager.newSubtask(epic2));
        Subtask updatedSubtask = manager.updateSingleSubtask(Subtask1);   // обновили сабтаск
        var alltasks = manager.getAllTasks();
        var allEpics = manager.getAllEpics();
        var allsubs = manager.getAllSubtasks();*/


    }


}
