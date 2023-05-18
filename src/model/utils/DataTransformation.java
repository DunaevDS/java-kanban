package model.utils;

import managers.historymanagers.HistoryManager;
import managers.taskmanagers.InMemoryTaskManager;
import managers.taskmanagers.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.enums.Status;
import model.enums.Type;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataTransformation {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    //преобразование из строки
    public static Task fromString(String value) {
        int epicID = 0;
        String[] st = value.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        int id = Integer.parseInt(st[0]);
        Type type = Type.valueOf(st[1]);
        String name = st[2];
        Status status = Status.valueOf(st[3]);
        String description = st[4].substring(1, st[4].length() - 1);

        switch (type) {
            case TASK:
                return new Task(id, name, status, description);
            case EPIC:
                return new Epic(id, name, status, description);
            case SUBTASK: {
                epicID = Integer.parseInt(st[5]);
                return new Subtask(id, name, status, description, epicID);
            }
            default:
                throw new IllegalArgumentException(ANSI_RED + "----> Передана неверная строка <----" + ANSI_RESET);
        }
    }

    // история в строку
    public static String historyToString(HistoryManager manager) {

        StringBuilder sb = new StringBuilder();

        for (Task task : manager.getHistory())
            if (task != null) {
                sb.append(task.getTaskId()).append(",");
            }
        if (sb.toString().equals("")) return sb.toString();
        else return sb.substring(0, sb.length() - 1);

    }

    // история из строки
    public static List<Integer> historyFromString(String value) {

        List<Integer> history = new LinkedList<>();

        for (String element : value.split(","))
            history.add(Integer.parseInt(element));

        return history;

    }
}
