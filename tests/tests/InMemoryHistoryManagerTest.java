package tests;

import entities.Epic;
import entities.Task;
import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static manager.InMemoryHistoryManager.MAX_HISTORY_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InMemoryHistoryManagerTest {

    @Test
    public void checkIfTasksAreSavedInHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        Task task = new Task("Уборка", "Убрать квартиру");
        historyManager.add(task);
        historyManager.add(task);
        List<Task> historyTasks = historyManager.getHistory();
        assertEquals(1, historyTasks.size());
        assertEquals(task, historyTasks.get(0));
    }

    @Test
    public void checkThatThePreviousTaskIsSavedInTheHistoryWhenAddingANewTask() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        Task task = new Task("Уборка", "Убрать квартиру");
        Epic epic = new Epic("Ремонт", "Отремонтировать квартиру");
        historyManager.add(task);
        historyManager.add(epic);
        List<Task> historyTasks = historyManager.getHistory();
        assertEquals(2, historyTasks.size());
        assertEquals(task, historyTasks.get(0));
        assertEquals(epic, historyTasks.get(1));
    }

    @Test
    public void checkThatTheOldestTaskIsDeletedWhenTheHistorySizeIsExceeded() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        Task task = new Task("Уборка", "Убрать квартиру");
        historyManager.add(task);

        for (int i = 0; i < MAX_HISTORY_SIZE; ++i) {
            Task newTask = new Task("Задача " + i, "Описание задачи " + i);
            historyManager.add(newTask);
        }

        List<Task> historyTasks = historyManager.getHistory();
        assertEquals(MAX_HISTORY_SIZE, historyTasks.size());
        // Проверяем, что самая старая задача (task) не в истории
        for (Task historyTask : historyTasks) {
            assertNotEquals(task.getId(), historyTask.getId());
        }
    }
}
