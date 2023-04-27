import managers.taskmanagers.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task1 = manager.createTask(manager.newTask()); //id=1
        Task task2 = manager.createTask(manager.newTask()); //id=2
        Epic epic1 = manager.createEpic(manager.newEpic()); //id=3
        Subtask Subtask1 = manager.createSubtask(manager.newSubtask(epic1)); //id=4
        Subtask Subtask2 = manager.createSubtask(manager.newSubtask(epic1)); //id=5
        Subtask Subtask3 = manager.createSubtask(manager.newSubtask(epic1)); //id=6
        Epic epic2 = manager.createEpic(manager.newEpic()); //id=7


        manager.getSingleTask(1);
        manager.getSingleTask(2);
        manager.getSingleTask(1);
        manager.getSingleTask(1);
        manager.getSingleEpic(3);
        manager.getSingleEpic(3);
        manager.getSingleEpic(3);

        var history1 = manager.getHistory();

        manager.getSingleEpic(7);
        manager.getSingleSubtask(4);
        manager.getSingleSubtask(4);
        manager.getSingleSubtask(5);
        manager.getSingleSubtask(6);

        var history2 = manager.getHistory();

        manager.deleteSingleTask(4);       //такой таски нету, в консоли вылезет ошибка
        manager.deleteSingleTask(1);
        manager.deleteSingleEpic(3);

        var history3 = manager.getHistory();



    }


}
