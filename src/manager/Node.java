package manager;

import entities.Task;

class Node {
    Task task;
    Node prev;
    Node next;

    Node(Task task, Node prev, Node next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }
}
