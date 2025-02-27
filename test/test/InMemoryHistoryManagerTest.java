package test;

import entities.Task;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager historyManager;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("Task 1", "Description 1");
        task2 = new Task("Task 2", "Description 2");
        task3 = new Task("Task 3", "Description 3");
    }

    @Test
    public void testAddTaskToHistory() {
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(List.of(task1, task2), history);
    }

    @Test
    public void testDuplicateTaskInHistory() {
        historyManager.add(task1);
        historyManager.add(task1); // Добавляем ту же задачу повторно

        List<Task> history = historyManager.getHistory();
        assertEquals(List.of(task1), history); // Должна остаться только одна запись
    }

    @Test
    public void testRemoveTaskFromHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(List.of(task2), history);
    }

    @Test
    public void testRemoveTaskFromMiddleOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(List.of(task1, task3), history);
    }

    @Test
    public void testRemoveTaskFromBeginningOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(List.of(task2, task3), history);
    }

    @Test
    public void testRemoveTaskFromEndOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task3.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(List.of(task1, task2), history);
    }

    @Test
    public void testRemoveOnlyTaskFromHistory() {
        historyManager.add(task1);
        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(List.of(), history);
    }

    @Test
    public void testRemoveFromEmptyHistory() {
        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(List.of(), history);
    }
}