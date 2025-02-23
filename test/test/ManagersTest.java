package test;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import manager.InMemoryTaskManager;
import manager.InMemoryHistoryManager;

public class ManagersTest {

    @Test
    public void testGetDefaultTaskManager() {
        TaskManager manager = Managers.getDefault();
        Assertions.assertNotNull(manager, "Таск менеджер не должен быть равен null");
    }

    @Test
    public void testGetDefaultHistoryManager() {
        HistoryManager manager = Managers.getDefaultHistory();
        Assertions.assertNotNull(manager, "Менеджер истории не должен быть равен null");
    }

    @Test
    public void testGetDefaultTaskManagerInstance() {
        TaskManager manager = Managers.getDefault();
        Assertions.assertTrue(manager instanceof InMemoryTaskManager, "Должен возвращаться InMemoryTaskManager");
    }

    @Test
    public void testGetDefaultHistoryManagerInstance() {
        HistoryManager manager = Managers.getDefaultHistory();
        Assertions.assertTrue(manager instanceof InMemoryHistoryManager, "Должен возвращаться InMemoryHistoryManager");
    }
}