package test;

import entities.Epic;
import entities.Subtask;
import entities.Task;
import enums.Status;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.assertEquals;

public class TaskTest {
    @Test
    public void testTaskEqualityById() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task(1, "Задача 2", "Описание задачи 2", Status.IN_PROGRESS);

        assertEquals(task1, task2);
    }

    @Test
    public void testEpicEqualityById() {
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic(1, "Эпик 2", "Описание эпика 2");

        assertEquals(epic1, epic2);
    }

    @Test
    public void testSubtaskEqualityById() {
        Subtask subtask1 = new Subtask(1, "Подзадача 1", "Описание подзадачи 1", 1);
        Subtask subtask2 = new Subtask(1, "Подзадача 2", "Описание подзадачи 2", 2);

        assertEquals(subtask1, subtask2);
    }

    @Test
    public void testTaskInequality() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task(2, "Задача 2", "Описание задачи 2", Status.NEW);
        Assertions.assertNotEquals(task1, task2, "Задачи с разными ID не должны быть равны");
    }

    @Test
    public void testEpicInequality() {
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic(2, "Эпик 2", "Описание эпика 2");
        Assertions.assertNotEquals(epic1, epic2, "Эпики с разными ID не должны быть равны");
    }

    @Test
    public void testSubtaskInequality() {
        Subtask subtask1 = new Subtask(1, "Подзадача 1", "Описание подзадачи 1", 1);
        Subtask subtask2 = new Subtask(2, "Подзадача 2", "Описание подзадачи 2", 1);
        Assertions.assertNotEquals(subtask1, subtask2, "Подзадачи с разными ID не должны быть равны");
    }
}