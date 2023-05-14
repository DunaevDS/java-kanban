package managers.taskmanagers;

import managers.taskmanagers.exceptions.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import managers.Managers;
import model.enums.Type;
import model.utils.DataTransformation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

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

    protected void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToFile.toFile()));
             BufferedReader br = new BufferedReader(new FileReader(pathToFile.toFile()))) {

            if (br.readLine() == null) {

                String content = "id,type,name,status,description,epic" + "\n";
                bw.write(content);

            }

            String values = DataTransformation.toString(this) + "\n" + DataTransformation.historyToString(historyManager);

            bw.write(values);

    } catch (IOException e) {
        throw new ManagerSaveException(ANSI_RED + "---> Ошибка записи в файл <---" + ANSI_RESET);}
    }

    // загрузка из файла
    public static FileBackedTasksManager load(Path filePath) {

        FileBackedTasksManager fileBackedTasksManager = Managers.getDefaultFileBackedManager();

        try {

            String fileName = Files.readString(filePath);

            String[] lines = fileName.split("\n");

            for (int i = 1; i < lines.length - 2; i++) {

                Task task = DataTransformation.FromString(lines[i]);
                String type = lines[i].split(", ")[1];

                if (Type.valueOf(type).equals(Type.TASK)) {

                    fileBackedTasksManager.createTask(task);
                    historyManager.add(fileBackedTasksManager.getSingleTask(task.getTaskId()));

                }

                if (Type.valueOf(type).equals(Type.EPIC)) {

                    Epic epic = (Epic) task;
                    fileBackedTasksManager.createEpic(epic);
                    historyManager.add(fileBackedTasksManager.getSingleEpic(epic.getTaskId()));

                }

                if (Type.valueOf(type).equals(Type.SUBTASK)) {

                    Subtask subtask = (Subtask) task;
                    fileBackedTasksManager.createSubtask(subtask);
                    historyManager.add(fileBackedTasksManager.getSingleSubtask(subtask.getTaskId()));

                }
                List<Integer> history = DataTransformation.historyFromString(lines[lines.length-1]);
            }

        } catch (IOException e) {

            throw new ManagerSaveException(ANSI_RED + " ---> Ошибка загрузки из файла <---" + ANSI_RESET);

        }

        return fileBackedTasksManager;

    }

}
