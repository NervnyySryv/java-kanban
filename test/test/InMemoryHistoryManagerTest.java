package test;

import entities.Task;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {

    @Test
    public void testAddTaskToHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    @Test
    public void testDuplicateTaskInHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Task 1", "Description 1");

        historyManager.add(task1);
        historyManager.add(task1); // Добавляем ту же задачу повторно

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size()); // Должна остаться только одна запись
        assertEquals(task1, history.get(0));
    }

    @Test
    public void testRemoveTaskFromHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task2, history.get(0));
    }

    @Test
    public void testRemoveTaskFromMiddleOfHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        Task task3 = new Task("Task 3", "Description 3");

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task3, history.get(1));
    }

    @Test
    public void testEmptyHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        List<Task> history = historyManager.getHistory();
        assertEquals(0, history.size(), "История должна быть пустой");
    }

    @Test
    public void testRemoveNonExistentTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Task 1", "Description 1");
        historyManager.add(task);
        historyManager.remove(999); // Несуществующий ID
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История не должна измениться");
    }

    @Test
    public void testAddNullTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(null);
        List<Task> history = historyManager.getHistory();
        assertEquals(0, history.size(), "История должна остаться пустой");
    }
}