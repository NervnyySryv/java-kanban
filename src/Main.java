import entities.Epic;
import entities.Subtask;
import entities.Task;
import manager.Managers;
import manager.TaskManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // 1. Создаем две задачи
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        manager.addTask(task1);
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        manager.addTask(task2);

        // 2. Создаем эпик с тремя подзадачами
        Epic epicWithSubtasks = new Epic("Эпик с подзадачами", "Описание эпика");
        manager.addEpic(epicWithSubtasks);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epicWithSubtasks.getId());
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epicWithSubtasks.getId());
        manager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", epicWithSubtasks.getId());
        manager.addSubtask(subtask3);

        // 3. Создаем эпик без подзадач
        Epic epicWithoutSubtasks = new Epic("Эпик без подзадач", "Пустой эпик");
        manager.addEpic(epicWithoutSubtasks);

        // 4. Запрашиваем задачи в разном порядке
        System.out.println("=== Запросы задач ===");
        manager.getTaskById(task1.getId());
        manager.getEpicById(epicWithSubtasks.getId());
        manager.getSubtaskById(subtask1.getId());
        manager.getEpicById(epicWithoutSubtasks.getId());
        manager.getTaskById(task2.getId());
        manager.getSubtaskById(subtask2.getId());

        // Выводим историю после первого запроса
        System.out.println("\nИстория после первого запроса:");
        printHistory(manager.getHistory());

        // 5. Проверка отсутствия повторов
        System.out.println("\n=== Повторные запросы ===");
        manager.getTaskById(task1.getId()); // Дубликат
        manager.getSubtaskById(subtask3.getId()); // Новая задача

        // История должна обновиться без дубликатов
        System.out.println("\nИстория после повторных запросов:");
        printHistory(manager.getHistory());

        // 6. Удаляем задачу из истории
        System.out.println("\n=== Удаление задачи ===");
        manager.removeTaskById(task1.getId());
        System.out.println("История после удаления задачи " + task1.getId() + ":");
        printHistory(manager.getHistory());

        // 7. Удаляем эпик с подзадачами
        System.out.println("\n=== Удаление эпика ===");
        manager.removeEpicById(epicWithSubtasks.getId());
        System.out.println("История после удаления эпика " + epicWithSubtasks.getId() + ":");
        printHistory(manager.getHistory());
    }

    // Метод для вывода истории
    private static void printHistory(List<Task> history) {
        if (history.isEmpty()) {
            System.out.println("История пуста.");
        } else {
            for (Task task : history) {
                System.out.println("ID: " + task.getId() + ", Тип: " + task.getClass().getSimpleName() + ", Название: " + task.getTitle());
            }
        }
    }
}