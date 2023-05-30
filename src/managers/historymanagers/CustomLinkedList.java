package managers.historymanagers;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class CustomLinkedList {

    private Node<Task> head;
    private Node<Task> tail;

    private int size = 0;

    public Node<Task> linkLast(Task task) {

        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;

        if (head == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;

        return newNode;
    }

    public List<Task> getTasks() {

        List<Task> tasks = new ArrayList<>();
        Node<Task> element = head;

        while (element != null) {
            tasks.add(element.data);
            element = element.next;
        }

        return tasks;
    }

    public void removeNode(Node<Task> node) {

        if (node == null || size == 0)
            return;

        if (node.equals(head)) {
            head = node.next;

            if (head != null)
                node.next.prev = null;

        } else {
            node.prev.next = node.next;

            if (node.next != null)
                node.next.prev = node.prev;
            else tail = node.prev;
        }
        size--;
    }

    public void clear() {
        head = null;
        tail = null;
    }
}
