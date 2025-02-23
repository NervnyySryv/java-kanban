package manager;

import entities.Task;

class Node {
    Task task; //Задача
    Node prev; //Предыдущий узел
    Node next; //Следующий узел

    Node(Task task, Node prev, Node next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }
}
