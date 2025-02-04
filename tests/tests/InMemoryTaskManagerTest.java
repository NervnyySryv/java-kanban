package tests;

import entities.Epic;
import entities.Subtask;
import entities.Task;
import enums.Status;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    private TaskManager manager;
    private Task task1;
    private Task task2;
    private Epic epic1;
    private Epic epic2;
    private Subtask subtask1;
    private Subtask subtask2;

    @BeforeEach
    public void setUp() {
        manager = Managers.getDefault();

        // Создание задач
        task1 = new Task("Задача 1", "Описание задачи 1");
        manager.addTask(task1);
        task2 = new Task("Задача 2", "Описание задачи 2");
        manager.addTask(task2);

        // Создание эпиков
        epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);
        epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.addEpic(epic2);

        // Создание подзадач
        subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId());
        manager.addSubtask(subtask1);
        subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1.getId());
        manager.addSubtask(subtask2);
    }

    @Test
    public void testAddAndGetTasks() {
        Task task2 = manager.getTaskById(task1.getId());
        assertNotNull(task2);

        Epic epic2 = manager.getEpicById(epic1.getId());
        assertNotNull(epic2);

        Subtask subtask2 = manager.getSubtaskById(subtask1.getId());
        assertNotNull(subtask2);
    }

    @Test
    public void testSubtaskCannotHaveItselfAsEpic() {
        Epic epic = new Epic("Домашние дела", "Навести порядок дома");
        manager.addEpic(epic);

        Subtask subtask1 = new Subtask("Посуда", "Помыть посуду", epic.getId());
        manager.addSubtask(subtask1);
        List<Subtask> subtasks = manager.getAllSubtasks();
        assertEquals(3, subtasks.size());
        Subtask subtaskFromManager = manager.getSubtaskById(subtask1.getId());
        assertNotNull(subtaskFromManager);
    }

    @Test
    public void testTaskImmutabilityOnAdd() {
        Task task1 = new Task("ДЗ", "Выполнить дз по 5 спринту");
        task1.setId(5);
        manager.addTask(task1);
        assertNotEquals(5, task1.getId());
        Task task2 = new Task("Уроки", "Делаем дз по русскому");
        task2.setId(1);
        manager.addTask(task2);
        List<Task> tasks = manager.getAllTasks();
        assertEquals(4, tasks.size()); // Теперь ожидаем 4 задачи
    }

    @Test
    public void testEpicCannotBeSubtaskOfItself() {
        // Попытка добавить эпик в самого себя в виде подзадачи
        Subtask selfSubtask = new Subtask("Самоподзадача", "Описание самоподзадачи", epic1.getId());
        selfSubtask.setEpicId(epic1.getId());
        manager.addSubtask(selfSubtask);

        Epic retrievedEpic = manager.getEpicById(epic1.getId());
        assertNotNull(retrievedEpic); // Добавляем проверку на null
        assertEquals(3, retrievedEpic.getSubtaskIds().size()); // Теперь ожидаем 3 подзадачи
        assertFalse(retrievedEpic.getSubtaskIds().contains(epic1.getId()));
    }

    @Test
    public void testGetAllTasks() {
        List<Task> allTasks = manager.getAllTasks();
        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(task1));
        assertTrue(allTasks.contains(task2));
    }

    @Test
    public void testGetAllEpics() {
        List<Epic> allEpics = manager.getAllEpics();
        assertEquals(2, allEpics.size());
        assertTrue(allEpics.contains(epic1));
        assertTrue(allEpics.contains(epic2));
    }

    @Test
    public void testGetAllSubtasks() {
        List<Subtask> allSubtasks = manager.getAllSubtasks();
        assertEquals(2, allSubtasks.size());
        assertTrue(allSubtasks.contains(subtask1));
        assertTrue(allSubtasks.contains(subtask2));
    }

    @Test
    public void testUpdateTask() {
        task1.setTitle("Обновленная задача");
        task1.setDescription("Обновленное описание задачи");
        task1.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task1);

        Task updatedTask = manager.getTaskById(task1.getId());
        assertNotNull(updatedTask);
        assertEquals("Обновленная задача", updatedTask.getTitle());
        assertEquals("Обновленное описание задачи", updatedTask.getDescription());
        assertEquals(Status.IN_PROGRESS, updatedTask.getStatus());
    }

    @Test
    public void testUpdateEpic() {
        // Добавляем подзадачи для проверки статуса эпика
        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);
        subtask2.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask2);

        // Обновляем эпик
        epic1.setTitle("Обновленный эпик");
        epic1.setDescription("Обновленное описание эпика");
        manager.updateEpic(epic1);

        Epic updatedEpic = manager.getEpicById(epic1.getId());
        assertNotNull(updatedEpic);
        assertEquals("Обновленный эпик", updatedEpic.getTitle());
        assertEquals("Обновленное описание эпика", updatedEpic.getDescription());
        assertEquals(Status.IN_PROGRESS, updatedEpic.getStatus());
    }

    @Test
    public void testClearAllTasks() {
        manager.clearAllTasks();
        List<Task> allTasks = manager.getAllTasks();
        assertEquals(0, allTasks.size());
    }

    @Test
    public void testClearAllEpics() {
        manager.clearAllEpics();
        List<Epic> allEpics = manager.getAllEpics();
        assertEquals(0, allEpics.size());
    }

    @Test
    public void testClearAllSubtasks() {
        manager.clearAllSubtasks();
        List<Subtask> allSubtasks = manager.getAllSubtasks();
        assertEquals(0, allSubtasks.size());
    }

    @Test
    public void testGetHistory() {
        manager.getTaskById(task1.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubtaskById(subtask1.getId());

        List<Task> history = manager.getHistory();
        assertEquals(3, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(epic1, history.get(1));
        assertEquals(subtask1, history.get(2));
    }
}