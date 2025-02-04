package manager;

import entities.Task;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final Deque<Task> history;
    public static final int MAX_HISTORY_SIZE = 10;

    public InMemoryHistoryManager() {
        this.history = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        history.remove(task); // Удаляем задачу, если она уже есть в истории
        history.addLast(task); // Добавляем задачу в конец истории
        if (history.size() > MAX_HISTORY_SIZE) {
            history.removeFirst(); // Если история превышает 10 элементов, удаляем задачу из начала
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}