package managers;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.Collection;

public interface TaskManager {

    // создание задачи
    Task createTask(Task task);  // создание задачи

    // создание эпика
    Epic createEpic(Epic epic);

    // создание подзадачи
    Subtask createSubtask(Subtask subtask);

    // получение списка всех задач
    Collection<Task> getAllTasks();

    // получение списка всех эпиков
    Collection<Epic> getAllEpics();

    // получение списка всех подзадач
    Collection<Subtask> getAllSubtasks();

    // удаление всех задач
    void deleteAllTasks();

    // удаление всех эпиков
    void deleteAllEpics();

    // удаление всех подзадач
    void deleteAllSubtasks();

    // удаление одной задачи
    void deleteSingleTask(int id);

    // удаление одного эпика
    void deleteSingleEpic(int epicID);

    // удаление одной подзадачи
    void deleteSingleSubtask(int id);

    // получение одной задачи
    Task getSingleTask(int id);

    // получение одного эпика
    Epic getSingleEpic(int id);

    // получение одной подзадачи
    Subtask getSingleSubtask(int id);

    // обновление одной задачи
    Task updateSingleTask(Task task);

    // обновление одного эпика
    Epic updateSingleEpic(Epic epic);

    // обновление одной подзадачи
    Subtask updateSingleSubtask(Subtask subtask);

    // печать всех задач
    void printAllTasks();

    // печать всех эпиков
    void printAllEpics();

    // печать всех подзадач
    void printAllSubtasks();

    // печать истории
    void printHistory();
}

