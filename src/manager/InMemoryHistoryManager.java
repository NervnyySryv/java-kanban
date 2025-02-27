package manager;

import entities.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> historyMap; // Хранит ID задачи и соответствующий узел
    private Node head; // Начало двусвязного списка
    private Node tail; // Конец двусвязного списка

    // Вложенный private static класс Node для реализации двусвязного списка
    private static class Node {
        Task task; // Задача, связанная с этим узлом
        Node prev; // Ссылка на предыдущий узел
        Node next; // Ссылка на следующий узел

        Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }

    public InMemoryHistoryManager() {
        this.historyMap = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return; // Игнорируем null задачи
        }

        int id = task.getId();
        removeNode(id); // Удаляем задачу, если она уже есть в истории
        linkLast(task); // Добавляем задачу в конец списка
    }

    @Override
    public void remove(int id) {
        removeNode(id); // Удаляем задачу из истории
    }

    @Override
    public List<Task> getHistory() {
        return getTasks(); // Возвращаем список задач из истории
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
        historyMap.put(task.getId(), newNode); // Добавляем задачу в HashMap
    }

    // Удаляет задачу из двусвязного списка и из HashMap
    private void removeNode(int id) {
        Node node = historyMap.get(id);
        if (node == null) {
            return; // Если задача не найдена, ничего не делаем
        }

        // Удаляем узел из списка
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

        historyMap.remove(id); // Удаляем задачу из HashMap
    }

    // Возвращает все задачи из двусвязного списка в виде списка
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