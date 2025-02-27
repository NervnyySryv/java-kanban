package test;

import entities.Epic;
import entities.Subtask;
import entities.Task;
import enums.Status;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    private TaskManager manager;
    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    public void setUp() {
        manager = new InMemoryTaskManager();

        // Инициализация тестовых данных
        task = new Task("Task 1", "Description 1");
        manager.addTask(task);

        epic = new Epic("Epic 1", "Description 1");
        manager.addEpic(epic);

        subtask = new Subtask("Subtask 1", "Description 1", epic.getId());
        manager.addSubtask(subtask);
    }

    @Test
    public void testAddAndGetTask() {
        Task retrievedTask = manager.getTaskById(task.getId());
        assertNotNull(retrievedTask, "Задача должна быть найдена");
        assertEquals(task, retrievedTask, "Задачи должны быть равны");
    }

    @Test
    public void testAddAndGetEpic() {
        Epic retrievedEpic = manager.getEpicById(epic.getId());
        assertNotNull(retrievedEpic, "Эпик должен быть найден");
        assertEquals(epic, retrievedEpic, "Эпики должны быть равны");
    }

    @Test
    public void testAddAndGetSubtask() {
        Subtask retrievedSubtask = manager.getSubtaskById(subtask.getId());
        assertNotNull(retrievedSubtask, "Подзадача должна быть найдена");
        assertEquals(subtask, retrievedSubtask, "Подзадачи должны быть равны");
    }

    @Test
    public void testRemoveTask() {
        manager.removeTaskById(task.getId());
        assertNull(manager.getTaskById(task.getId()), "Задача должна быть удалена");
    }

    @Test
    public void testRemoveEpic() {
        manager.removeEpicById(epic.getId());
        assertNull(manager.getEpicById(epic.getId()), "Эпик должен быть удален");
        assertEquals(0, manager.getSubtasksByEpicId(epic.getId()).size(), "Подзадачи эпика должны быть удалены");
    }

    @Test
    public void testRemoveSubtask() {
        manager.removeSubtaskById(subtask.getId());
        assertNull(manager.getSubtaskById(subtask.getId()), "Подзадача должна быть удалена");
        assertEquals(0, manager.getSubtasksByEpicId(epic.getId()).size(), "Подзадача должна быть удалена из эпика");
    }

    @Test
    public void testUpdateTask() {
        task.setTitle("Updated Title");
        task.setDescription("Updated Description");
        task.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task);

        Task updatedTask = manager.getTaskById(task.getId());
        assertEquals("Updated Title", updatedTask.getTitle(), "Название задачи должно быть обновлено");
        assertEquals("Updated Description", updatedTask.getDescription(), "Описание задачи должно быть обновлено");
        assertEquals(Status.IN_PROGRESS, updatedTask.getStatus(), "Статус задачи должен быть обновлен");
    }

    @Test
    public void testUpdateEpic() {
        epic.setTitle("Updated Epic Title");
        epic.setDescription("Updated Epic Description");
        manager.updateEpic(epic);

        Epic updatedEpic = manager.getEpicById(epic.getId());
        assertEquals("Updated Epic Title", updatedEpic.getTitle(), "Название эпика должно быть обновлено");
        assertEquals("Updated Epic Description", updatedEpic.getDescription(), "Описание эпика должно быть обновлено");
    }

    @Test
    public void testUpdateSubtask() {
        subtask.setTitle("Updated Subtask Title");
        subtask.setDescription("Updated Subtask Description");
        subtask.setStatus(Status.DONE);
        manager.updateSubtask(subtask);

        Subtask updatedSubtask = manager.getSubtaskById(subtask.getId());
        assertEquals("Updated Subtask Title", updatedSubtask.getTitle(), "Название подзадачи должно быть обновлено");
        assertEquals("Updated Subtask Description", updatedSubtask.getDescription(), "Описание подзадачи должно быть обновлено");
        assertEquals(Status.DONE, updatedSubtask.getStatus(), "Статус подзадачи должен быть обновлен");
    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = manager.getAllTasks();
        assertEquals(1, tasks.size(), "Должна быть одна задача");
        assertEquals(task, tasks.get(0), "Задачи должны совпадать");
    }

    @Test
    public void testGetAllEpics() {
        List<Epic> epics = manager.getAllEpics();
        assertEquals(1, epics.size(), "Должен быть один эпик");
        assertEquals(epic, epics.get(0), "Эпики должны совпадать");
    }

    @Test
    public void testGetAllSubtasks() {
        List<Subtask> subtasks = manager.getAllSubtasks();
        assertEquals(1, subtasks.size(), "Должна быть одна подзадача");
        assertEquals(subtask, subtasks.get(0), "Подзадачи должны совпадать");
    }

    @Test
    public void testGetSubtasksByEpicId() {
        List<Subtask> subtasks = manager.getSubtasksByEpicId(epic.getId());
        assertEquals(1, subtasks.size(), "Должна быть одна подзадача в эпике");
        assertEquals(subtask, subtasks.get(0), "Подзадачи должны совпадать");
    }

    @Test
    public void testUpdateEpicStatus() {
        subtask.setStatus(Status.DONE);
        manager.updateSubtask(subtask);

        Epic updatedEpic = manager.getEpicById(epic.getId());
        assertEquals(Status.DONE, updatedEpic.getStatus(), "Статус эпика должен быть обновлен");
    }

    @Test
    public void testRemoveNonExistentTask() {
        manager.removeTaskById(999); // Несуществующий ID
        assertNull(manager.getTaskById(999), "Задача с несуществующим ID не должна быть найдена");
    }

    @Test
    public void testRemoveNonExistentEpic() {
        manager.removeEpicById(999); // Несуществующий ID
        assertNull(manager.getEpicById(999), "Эпик с несуществующим ID не должен быть найден");
    }

    @Test
    public void testRemoveNonExistentSubtask() {
        manager.removeSubtaskById(999); // Несуществующий ID
        assertNull(manager.getSubtaskById(999), "Подзадача с несуществующим ID не должна быть найдена");
    }
}