package manager;

import entities.Epic;
import entities.Subtask;
import entities.Task;

import java.util.List;

public interface TaskManager {

    // Методы для Task
    List<Task> getAllTasks();

    void clearAllTasks();

    Task getTaskById(int id);

    void addTask(Task task);

    void updateTask(Task task);

    void removeTaskById(int id);

    // Методы для Epic
    List<Epic> getAllEpics();

    void clearAllEpics();

    Epic getEpicById(int id);

    void addEpic(Epic epic);

    void updateEpic(Epic epic);

    void removeEpicById(int id);

    // Методы для Subtask
    List<Subtask> getAllSubtasks();

    void clearAllSubtasks();

    Subtask getSubtaskById(int id);

    void addSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    void removeSubtaskById(int id);

    // Дополнительные методы
    List<Subtask> getSubtasksByEpicId(int epicId);

    // Метод для получения истории просмотров
    List<Task> getHistory();

}