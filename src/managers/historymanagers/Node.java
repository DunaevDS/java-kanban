package managers.historymanagers;

class Node<E> {
    public E data;
    public Node<E> next;
    public Node<E> prev;

    public Node(Node<E> prev, E element, Node<E> next) {
        this.data = element;
        this.next = next;
        this.prev = prev;
    }
}
