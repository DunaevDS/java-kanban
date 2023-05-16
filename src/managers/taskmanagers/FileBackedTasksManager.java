package managers.taskmanagers;

import managers.taskmanagers.exceptions.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import managers.Managers;
import model.enums.Type;
import model.utils.DataTransformation;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    private static final FileBackedTasksManager fileBackedTasksManager = Managers.getDefaultFileBackedManager();
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private static final Path pathToFile = Path.of("src\\test.csv");

    @Override
    public Task createTask(Task task) {
        Task savedTask = super.createTask(task);
        save();

        return savedTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic savedEpic = super.createEpic(epic);
        save();

        return savedEpic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        Subtask savedSubtask = super.createSubtask(subtask);
        save();

        return savedSubtask;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }


    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteSingleTask(int id) {
        super.deleteSingleTask(id);
        save();
    }

    @Override
    public void deleteSingleEpic(int epicID) {
        super.deleteSingleEpic(epicID);
        save();
    }

    @Override
    public void deleteSingleSubtask(int id) {
        super.deleteSingleSubtask(id);
        save();
    }

    @Override
    public Task getSingleTask(int id) {
        Task savedTask = super.getSingleTask(id);
        save();

        return savedTask;
    }

    @Override
    public Epic getSingleEpic(int id) {
        Epic savedEpic = super.getSingleEpic(id);
        save();

        return savedEpic;
    }

    @Override
    public Subtask getSingleSubtask(int id) {
        Subtask savedSubtask = super.getSingleSubtask(id);
        save();

        return savedSubtask;
    }

    @Override
    public Task updateSingleTask(Task task) {
        Task savedTask = super.updateSingleTask(task);
        save();

        return savedTask;
    }

    @Override
    public Epic updateSingleEpic(Epic epic) {
        Epic savedEpic = super.updateSingleEpic(epic);
        save();

        return savedEpic;
    }

    @Override
    public Subtask updateSingleSubtask(Subtask subtask) {
        Subtask savedSubtask = super.updateSingleSubtask(subtask);
        save();

        return savedSubtask;
    }

    @Override
    public Type getType(int id) {
        return super.getType(id);
    }

    protected void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToFile.toFile(), StandardCharsets.UTF_8));
             BufferedReader br = new BufferedReader(new FileReader(pathToFile.toFile(), StandardCharsets.UTF_8))) {

            if (br.readLine() == null) {

                String header = "id,type,name,status,description,epic";
                bw.write(header);       // Чем newLine() лучше чем + "\n"? Лучше читаемость?
                bw.newLine();

            }

            String values = DataTransformation.toString(this) + "\n" + DataTransformation.historyToString(historyManager);

            bw.write(values);

        } catch (IOException e) {
            throw new ManagerSaveException(ANSI_RED + "---> Ошибка записи в файл <---" + ANSI_RESET);
        }
    }

    // загрузка из файла
    public static FileBackedTasksManager load(Path filePath) {

        int counterId = 0;

        try {

            String fileName = Files.readString(filePath);

            String[] lines = fileName.split("\n");

            List<Integer> historyLine = DataTransformation.historyFromString(lines[lines.length - 1]);

            for (int i = 1; i < lines.length - 2; i++) {

                Task task = DataTransformation.fromString(lines[i]);

                Type taskType = task.getType();


                if (task.getTaskId() > counterId)
                    counterId = task.getTaskId();

                switch (taskType) {
                    case TASK: {
                        fileBackedTasksManager.createTask(task);
                        break;
                    }
                    case EPIC: {
                        Epic epic = (Epic) task;
                        fileBackedTasksManager.createEpic(epic);
                        break;
                    }
                    case SUBTASK: {
                        Subtask subtask = (Subtask) task;
                        fileBackedTasksManager.createSubtask(subtask);
                        break;
                    }
                }

            }
            for (Integer value : historyLine) {

                Task task = fileBackedTasksManager.tasks.get(value);
                if (task != null) historyManager.add(task);

                else {
                    task = fileBackedTasksManager.epics.get(value);
                    if (task != null) historyManager.add(task);

                    else {
                        task = fileBackedTasksManager.subtasks.get(value);
                        historyManager.add(task);
                    }
                }
            }


        } catch (
                IOException e) {

            throw new ManagerSaveException(ANSI_RED + " ---> Ошибка загрузки из файла <---" + ANSI_RESET);
        }

        return fileBackedTasksManager;
    }

    public static void main(String[] args) {

        //новый экземпляр объекта FileBackedTasksManager
        FileBackedTasksManager manager = FileBackedTasksManager.load(Path.of("src\\test.csv"));

        System.out.println("Восстановление из истории: ");
        for (Task task : manager.getHistory()) {
            System.out.println(task);

        }

        System.out.println("------------");

        Task task1 = fileBackedTasksManager.createTask(fileBackedTasksManager.newTask());
        Task task2 = fileBackedTasksManager.createTask(fileBackedTasksManager.newTask());
        Epic epic = fileBackedTasksManager.createEpic(fileBackedTasksManager.newEpic());
        Subtask subtask = fileBackedTasksManager.createSubtask(fileBackedTasksManager.newSubtask(epic));


        fileBackedTasksManager.getSingleTask(1);
        fileBackedTasksManager.getSingleTask(2);
        fileBackedTasksManager.getSingleEpic(3);
        fileBackedTasksManager.getSingleTask(1);    // повторный вызов таски с id=1. Очередь вызовов будет [2,3,1,4,5]
        fileBackedTasksManager.getSingleSubtask(4);


        for (Task task : fileBackedTasksManager.getHistory()) {

            System.out.println(task);
        }
    }
}
