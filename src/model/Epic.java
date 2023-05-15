package model;

import model.enums.Status;
import model.enums.Type;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subs;

    public Epic(String name,
                String description,
                int id,
                Status status) {
        super(name, description, id, status);
        this.type = Type.EPIC;
        this.subs = new ArrayList<>();
    }

    // конструктор для преобразования из строки
    public Epic(int id,
                String type,
                String name,
                Status status,
                String description) {
        super(name, description, id, status);
        this.type = Type.EPIC;
        this.subs = new ArrayList<>();
    }

    public ArrayList<Integer> getSubs() {
        return subs;
    }

    public void addSubtask(int id) {
        subs.add(id);
    }

    public void removeSubtask(int id) {
        subs.remove(id);
    }

    public void removeAllSubtasks() {
        subs.clear();
    }

    public void updateSubtask(int id) {
        subs.set(subs.indexOf(id), id);
    }

    @Override
    public  String toString(){
        return  taskId
                + "," + type
                + "," + name
                + "," + status
                + ",\"" + description
                + "\",";
    }
}

