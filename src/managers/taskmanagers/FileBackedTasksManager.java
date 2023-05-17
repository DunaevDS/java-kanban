package managers.taskmanagers;

import managers.taskmanagers.exceptions.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import managers.Managers;
import model.enums.Type;
import model.utils.DataTransformation;
import modeel

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

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

    @Override
    public Type getType(int id) {
        return super.getType(id);
    }


    protected void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\test.csv", StandardCharsets.UTF_8));   // почему такая запись лучше чем моя прошлая?
             BufferedReader br = new BufferedReader(new FileReader("src\\test.csv", StandardCharsets.UTF_8))) {

            if (br.readLine() == null) {

                String header = "id,type,name,status,description,epic";
                bw.write(header);
                bw.newLine();

            }

            String values = toString(this);
            String historyLine = DataTransformation.historyToString(historyManager);
            bw.write(values);
            bw.newLine();
            bw.write(historyLine);

        } catch (IOException e) {
            throw new ManagerSaveException(ANSI_RED + "---> Ошибка записи в файл <---" + ANSI_RESET);
        }
    }

    // загрузка из файла
    public static FileBackedTasksManager load(Path filePath) {

        final FileBackedTasksManager taskManager = Managers.getDefaultFileBackedManager();  // как я понял, этот "перенос" сделан
                                                                                            // по причине того, что БэкМенеджером
        int counterId = 0;                                                                  // пользуемся только в этом методе?

        try {

            String fileName = Files.readString(filePath);

            String[] lines = fileName.split("\n");

            List<Integer> historyLine = DataTransformation.historyFromString(lines[lines.length - 1]);

            for (int i = 1; i < lines.length - 2; i++) {

                Task task = DataTransformation.fromString(lines[i]);

                Type taskType = task.getType();

                
                if (task.getTaskId() > counterId)
                    counterId = task.getTaskId();

                taskManager.addTask(task, taskType);

            }
            for (Integer value : historyLine) {

                Task task = taskManager.tasks.get(value);      // метод load статичный по ТЗ, поэтому
                if (task != null) historyManager.add(task);    // Task task = tasks.get(value) нельзя

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
                break;
            }
        }
    }

    // преобразование в строку
    public static String toString(TaskManager taskManager) {            // посчитал что данный метод обязательно должен быть в утилитарном классе
                                                                        // т.к. статик и схож с другими методами, которые помещены в тот класс.
        StringBuilder sb = new StringBuilder();                         // Если метод статик, то как определить должен ли он быть в утил. классе или нет?

        List<Task> tasks = new ArrayList<>(taskManager.getAllTasks());

        tasks.addAll(taskManager.getAllEpics());

        tasks.addAll(taskManager.getAllSubtasks());

        for (Task task : tasks) {

            sb.append(task.toString()).append("\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        //новый экземпляр объекта FileBackedTasksManager
        FileBackedTasksManager manager = FileBackedTasksManager.load(Path.of("src\\test.csv"));

        System.out.println("Восстановление из истории: ");
        for (Task task : manager.getHistory()) {
            System.out.println(task);

        }

        System.out.println("------------");

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task11 = inMemoryTaskManager.createTask(inMemoryTaskManager.newTask()); // проверка что id = 5;

        .getSingleTask(2);
        fileBackedTasksManager.getSingleEpic(3);
        fileBackedTasksManager.getSingleTask(1);    // Очередь вызовов [2,3,1,4,5]
        fileBackedTasksManager.getSingleSubtask(4);


        for (Task task : fileBackedTasksManager.getHistory()) {

            System.out.println(task);
        }
    }
}
