package model.utils;

import model.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    // компаратор
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getStartTime().compareTo(o2.getStartTime());
    }
}