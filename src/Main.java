import managers.InMemoryTaskManager;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        /*Task task1 = taskManager.createTask(taskManager.newTask());                    // Создаем задачи
        Task task2 = taskManager.createTask(taskManager.newTask());
        Task task3 = taskManager.createTask(taskManager.newTask());
        Task task4 = taskManager.createTask(taskManager.newTask());
        var returnedTasks = taskManager.getAllTasks();            // получаем все задачи

        Task getSingleTask = taskManager.getSingleTask(1);                          // получаем задачу по нужному ID
        taskManager.deleteSingleTask(2);                                               // удаляем одну задачу
        Task updateTask = taskManager.updateSingleTask(task1);                       // обновляем задачу
        taskManager.printAllTasks();                                                    // принт задач
        taskManager.deleteAllTasks();*/                                                 // удаляем задачи



        /*Epic epic = taskManager.createEpic(taskManager.newEpic());              // создаем эпик
        Epic epic2 = taskManager.createEpic(taskManager.newEpic());
        Epic epic3 = taskManager.createEpic(taskManager.newEpic());
        var returnedEpics = taskManager.getAllEpics();        // получаем все эпики
        Subtask createSubtask1 = taskManager.createSubtask(taskManager.newSubtask(epic));   // создаем сабтаски
        Subtask createSubtask2 = taskManager.createSubtask(taskManager.newSubtask(epic));
        taskManager.printAllEpics();                                                 // принт эпиков
        taskManager.printAllSubtasks();                                              // принт подзадач
        taskManager.deleteSingleEpic(1);                                       // удаляем эпик, содержащий сабтаски
        taskManager.deleteAllEpics();*/                                                // удаляем эпики



        /*Epic epic = taskManager.createEpic(taskManager.newEpic());                           // создаем эпик и сабтаски для него
        Subtask Subtask1 = taskManager.createSubtask(taskManager.newSubtask(epic));
        Subtask Subtask2 = taskManager.createSubtask(taskManager.newSubtask(epic));
        Subtask1.setStatus(Status.DONE);                                                        // меняем статус задачи
        epic = taskManager.updateSingleEpic(epic);                                             // смотрим, что статус эпика меняется после обновления
        Subtask Subtask3 = taskManager.createSubtask(taskManager.newSubtask(epic));     // здесь статус эпика меняется
        Subtask getSingleSubtask = taskManager.getSingleSubtask(2);                 // получаем сабтаск
        taskManager.deleteSingleSubtask(3);                                              // удаляем сабтаск
        Subtask updatedSubtask = taskManager.updateSingleSubtask(Subtask1);          // обновляем сабтаск
        taskManager.deleteAllSubtasks();*/


        /*Epic epic = taskManager.createEpic(taskManager.newEpic());                        // создаем эпик и сабтаски для него
        Subtask Subtask1 = taskManager.createSubtask(taskManager.newSubtask(epic));
        Subtask Subtask2 = taskManager.createSubtask(taskManager.newSubtask(epic));
        Subtask1.setStatus(Status.DONE);
        epic = taskManager.updateSingleEpic(epic);
        Subtask2.setStatus(Status.DONE);
        epic = taskManager.updateSingleEpic(epic);*/                                  // статус эпика меняется на DONE


        /*Epic epic3 = taskManager.createEpic(taskManager.newEpic());
        Subtask Subtask3 = taskManager.createSubtask(taskManager.newSubtask(epic2));
        Subtask Subtask4 = taskManager.createSubtask(taskManager.newSubtask(epic2));
        Subtask updatedSubtask = taskManager.updateSingleSubtask(Subtask1);   // обновили сабтаск
        var alltasks = taskManager.getAllTasks();
        var allEpics = taskManager.getAllEpics();
        var allsubs = taskManager.getAllSubtasks();*/


    }


}
