package tests;

import entities.Epic;
import entities.Subtask;
import entities.Task;
import enums.Status;
import org.junit.Test;

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


}