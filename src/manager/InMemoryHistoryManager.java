package manager;

import entities.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> historyMap; // Хранит ID задачи и соответствующий узел
    private Node head; // Начало списка
    private Node tail; // Конец списка

    public InMemoryHistoryManager() {
        this.historyMap = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        int id = task.getId();
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id)); // Удаляем задачу, если она уже есть в истории
        }

        linkLast(task); // Добавляем задачу в конец списка
        historyMap.put(id, tail); // Обновляем HashMap
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id)); // Удаляем узел из списка
            historyMap.remove(id); // Удаляем задачу из HashMap
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    // Добавляет задачу в конец двусвязного списка
    private void linkLast(Task task) {
        Node newNode = new Node(task, tail, null);
        if (tail == null) {
            head = newNode; // Если список пуст, новый узел становится головой
        } else {
            tail.next = newNode; // Иначе добавляем новый узел в конец
        }
        tail = newNode; // Новый узел становится хвостом
    }

    // Удаляет узел из двусвязного списка
    private void removeNode(Node node) {
        if (node == null) {
            return;
        }

        if (node.prev != null) {
            node.prev.next = node.next; // Обновляем ссылку предыдущего узла
        } else {
            head = node.next; // Если удаляем голову, обновляем голову
        }

        if (node.next != null) {
            node.next.prev = node.prev; // Обновляем ссылку следующего узла
        } else {
            tail = node.prev; // Если удаляем хвост, обновляем хвост
        }
    }

    // Возвращает все задачи из двусвязного списка в виде ArrayList
    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }
        return tasks;
    }
}