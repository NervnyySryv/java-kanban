import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Создание задачи
        Task task1 = new Task(manager.generateNextId(), "Переезд", "Переехать в новую квартиру", Status.NEW);
        manager.addTask(task1);

        // Создание эпика и подзадач
        Epic epic1 = new Epic(manager.generateNextId(), "Организация праздника", "Организовать большой семейный праздник", Status.NEW);
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask(manager.generateNextId(), "Приготовление еды", "Приготовить еду на праздник", Status.NEW, epic1);
        manager.addSubtask(subtask1);

        Subtask subtask2 = new Subtask(manager.generateNextId(), "Декорирование", "Декорировать помещение", Status.NEW, epic1);
        manager.addSubtask(subtask2);

        // Обновление статуса подзадачи
        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);

        // Проверка статуса эпика после одной выполненной подзадачи
        System.out.println("Статус эпика после одной выполненной подзадачи: " + epic1.getStatus());

        subtask2.setStatus(Status.DONE);
        manager.updateSubtask(subtask2);

        // Проверка статуса эпика после всех выполненных подзадач
        System.out.println("Статус эпика после всех выполненных подзадач: " + epic1.getStatus());

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