package tests;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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
}