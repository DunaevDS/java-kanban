package managers.taskmanagers;

import managers.historymanagers.HistoryManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class FileBackedTasksManager extends InMemoryTaskManager {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\test.csv", StandardCharsets.UTF_8));
             BufferedReader br = new BufferedReader(new FileReader("src\\test.csv", StandardCharsets.UTF_8))) {

            if (br.readLine() == null) {

                String header = "id,type,name,status,description,startTime,duration,endTime,epic";
                bw.write(header);
                bw.newLine();

            }
            // преобразование в строку
            List<Task> tasks = new ArrayList<>(getAllTasks());

            if (getAllEpics() != null )
                tasks.addAll(getAllEpics());

            if (getAllSubtasks() != null)
                tasks.addAll(getAllSubtasks());

            String result = tasks.stream()
                    .map(Task::toString)
                    .collect(Collectors.joining("\r\n"));

            String historyLine = DataTransformation.historyToString(historyManager);

            bw.write(result);
            bw.newLine();
            bw.write(historyLine);


        } catch (IOException e) {
            throw new ManagerSaveException(ANSI_RED + "---> Ошибка записи в файл <---" + ANSI_RESET);
        }
    }

    // загрузка из файла
    public FileBackedTasksManager load(Path filePath) {

        final FileBackedTasksManager taskManager = Managers.getDefaultFileBackedManager();

        int counterId = 0;

        try {

            String fileName = Files.readString(filePath);

            String[] lines = fileName.split("\r\n");

            List<Integer> historyLine = DataTransformation.historyFromString(lines[lines.length - 1]);

            for (int i = 1; i < lines.length - 1; i++) {
                Task task = DataTransformation.fromString(lines[i]);
                Type taskType = task.getType();

                if (task.getTaskId() > counterId)
                    counterId = task.getTaskId();

                taskManager.addTask(task, taskType);

            }
            for (Integer value : historyLine) {

                Task task = taskManager.tasks.get(value);
                if (task != null) historyManager.add(task);

                else {
                    task = taskManager.epics.get(value);
                    if (task != null) historyManager.add(task);

                    else {
                        task = taskManager.subtasks.get(value);
                        historyManager.add(task);
                    }
                }
            }

            taskManager.setId(counterId);

        } catch (
                IOException e) {

            throw new ManagerSaveException(ANSI_RED + " ---> Ошибка загрузки из файла <---" + ANSI_RESET);
        }

        return taskManager;
    }

    private void addTask(Task task, Type taskType) {
        switch (taskType) {
            case TASK: {
                tasks.put(task.getTaskId(), task);
                break;
            }
            case EPIC: {
                Epic epic = (Epic) task;
                epics.put(task.getTaskId(), epic);
                break;
            }
            case SUBTASK: {
                Subtask subtask = (Subtask) task;
                subtasks.put(task.getTaskId(),subtask);

                Epic epic = epics.get(subtask.getEpicId());

                epic.addSubtask(task.getTaskId());
                break;
            }
        }
    }
}