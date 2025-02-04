package manager;

public class Managers {

    private Managers() {
    }

    // Метод для получения экземпляра TaskManager
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    // Метод для получения экземпляра HistoryManager
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}