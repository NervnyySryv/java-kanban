package test;

import entities.Epic;
import entities.Subtask;
import entities.Task;
import enums.Status;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerTest {

    @Test
    public void testAddAndRemoveTask() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task("Task 1", "Description 1");
        manager.addTask(task);

        assertEquals(1, manager.getAllTasks().size());
        manager.removeTaskById(task.getId());
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void testAddAndRemoveEpicWithSubtasks() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("Epic 1", "Description 1");
        manager.addEpic(epic);

        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", epic.getId());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        assertEquals(2, manager.getSubtasksByEpicId(epic.getId()).size());
        manager.removeEpicById(epic.getId());
        assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    public void testUpdateEpicStatus() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("Epic 1", "Description 1");
        manager.addEpic(epic);

        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", epic.getId());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subtask2.setStatus(Status.DONE);
        manager.updateSubtask(subtask2);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void testRemoveSubtaskFromEpic() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("Epic 1", "Description 1");
        manager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", "Description 1", epic.getId());
        manager.addSubtask(subtask);

        assertEquals(1, manager.getSubtasksByEpicId(epic.getId()).size());
        manager.removeSubtaskById(subtask.getId());
        assertEquals(0, manager.getSubtasksByEpicId(epic.getId()).size());
    }

    @Test
    public void testTaskSetters() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task("Task 1", "Description 1");
        manager.addTask(task);

        task.setTitle("New Title");
        task.setDescription("New Description");
        task.setStatus(Status.IN_PROGRESS);

        Task updatedTask = manager.getTaskById(task.getId());
        assertEquals("New Title", updatedTask.getTitle());
        assertEquals("New Description", updatedTask.getDescription());
        assertEquals(Status.IN_PROGRESS, updatedTask.getStatus());
    }

    @Test
    public void testUpdateNonExistentTask() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task("Task 1", "Description 1");
        manager.updateTask(task); // Несуществующая задача
        assertEquals(0, manager.getAllTasks().size(), "Задача не должна быть добавлена");
    }

    @Test
    public void testRemoveNonExistentTask() {
        TaskManager manager = new InMemoryTaskManager();
        manager.removeTaskById(999); // Несуществующий ID
        assertEquals(0, manager.getAllTasks().size(), "Список задач должен остаться пустым");
    }

    @Test
    public void testAddSubtaskToNonExistentEpic() {
        TaskManager manager = new InMemoryTaskManager();
        Subtask subtask = new Subtask("Subtask 1", "Description 1", 999); // Несуществующий ID эпика
        manager.addSubtask(subtask);
        assertEquals(0, manager.getAllSubtasks().size(), "Подзадача не должна быть добавлена");
    }

    @Test
    public void testUpdateEpicStatusWithoutSubtasks() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("Epic 1", "Description 1");
        manager.addEpic(epic);
        assertEquals(Status.NEW, epic.getStatus(), "Статус эпика без подзадач должен быть NEW");
    }
}