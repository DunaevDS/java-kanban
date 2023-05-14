import managers.Managers;
import managers.taskmanagers.FileBackedTasksManager;
import managers.taskmanagers.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.utils.DataTransformation;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();
        FileBackedTasksManager fileManager = Managers.getDefaultFileBackedManager();

        Task task1 = fileManager.createTask(fileManager.newTask());                  //id=1
        Task task2 = fileManager.createTask(fileManager.newTask());                  //id=2
        Epic epic1 = fileManager.createEpic(fileManager.newEpic());                  //id=3
        /*Subtask Subtask1 = fileManager.createSubtask(fileManager.newSubtask(epic1)); //id=4
        Subtask Subtask2 = fileManager.createSubtask(fileManager.newSubtask(epic1)); //id=5
        Subtask Subtask3 = fileManager.createSubtask(fileManager.newSubtask(epic1)); //id=6
        Epic epic2 = fileManager.createEpic(fileManager.newEpic());                  //id=7*/


        fileManager.getSingleTask(1);
        fileManager.getSingleTask(2);
        fileManager.getSingleEpic(3);
        fileManager.getSingleTask(1);
        fileManager.getSingleTask(1);
        /*ileManager.getSingleSubtask(4);
        fileManager.getSingleSubtask(5);*/

        FileBackedTasksManager newFileManager = FileBackedTasksManager.load(Path.of("src\\test.csv"));

    }


}
