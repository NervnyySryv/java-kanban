import entities.Epic;
import entities.Subtask;
import entities.Task;
import enums.Status;
import manager.Managers;
import manager.TaskManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault(); // Используем Managers для создания менеджера


        // Создание задачи
        Task task1 = new Task("Переезд", "Переехать в новую квартиру", Status.NEW);
        manager.addTask(task1);
        System.out.println("Задача создана: ID = " + task1.getId());

        // Создание эпика и подзадач
        Epic epic1 = new Epic("Организация праздника", "Организовать большой семейный праздник");
        manager.addEpic(epic1);
        System.out.println("Эпик создан: ID = " + epic1.getId());

        Subtask subtask1 = new Subtask("Приготовление еды", "Приготовить еду на праздник", epic1.getId());
        manager.addSubtask(subtask1);
        System.out.println("Подзадача создана: ID = " + subtask1.getId());

        Subtask subtask2 = new Subtask("Декорирование", "Декорировать помещение", epic1.getId());
        manager.addSubtask(subtask2);
        System.out.println("Подзадача создана: ID = " + subtask2.getId());

        // Обновление статуса подзадачи
        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);
        System.out.println("Статус подзадачи обновлен: ID = " + subtask1.getId() + ", Статус = " + subtask1.getStatus());

        // Проверка статуса эпика после одной выполненной подзадачи
        System.out.println("Статус эпика после одной выполненной подзадачи: ID = " + epic1.getId() + ", Статус = " + manager.getEpicById(epic1.getId()).getStatus());

        subtask2.setStatus(Status.DONE);
        manager.updateSubtask(subtask2);
        System.out.println("Статус подзадачи обновлен: ID = " + subtask2.getId() + ", Статус = " + subtask2.getStatus());

        // Проверка статуса эпика после всех выполненных подзадач
        System.out.println("Статус эпика после всех выполненных подзадач: ID = " + epic1.getId() + ", Статус = " + manager.getEpicById(epic1.getId()).getStatus());

        // Получение всех задач
        List<Task> allTasks = manager.getAllTasks();
        System.out.println("Все задачи:");
        for (Task task : allTasks) {
            System.out.println("ID: " + task.getId() + ", Название: " + task.getTitle() + ", Статус: " + task.getStatus());
        }

        // Получение всех эпиков
        List<Epic> allEpics = manager.getAllEpics();
        System.out.println("Все эпики:");
        for (Epic epic : allEpics) {
            System.out.println("ID: " + epic.getId() + ", Название: " + epic.getTitle() + ", Статус: " + epic.getStatus());
        }

        // Получение всех подзадач
        List<Subtask> allSubtasks = manager.getAllSubtasks();
        System.out.println("Все подзадачи:");
        for (Subtask subtask : allSubtasks) {
            System.out.println("ID: " + subtask.getId() + ", Название: " + subtask.getTitle() + ", Статус: " + subtask.getStatus());
        }

        // Получение подзадач по эпику
        List<Subtask> subtasksByEpic = manager.getSubtasksByEpicId(epic1.getId());
        System.out.println("Подзадачи эпика с ID " + epic1.getId() + ":");
        for (Subtask subtask : subtasksByEpic) {
            System.out.println("ID: " + subtask.getId() + ", Название: " + subtask.getTitle() + ", Статус: " + subtask.getStatus());
        }

        // Удаление задачи
        manager.removeTaskById(task1.getId());
        System.out.println("Задача с ID " + task1.getId() + " удалена.");

        // Удаление эпика
        manager.removeEpicById(epic1.getId());
        System.out.println("Эпик с ID " + epic1.getId() + " удален.");
    }
}