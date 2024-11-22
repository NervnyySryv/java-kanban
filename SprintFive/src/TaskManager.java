import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private int nextId;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.nextId = 1;
    }

    public int generateNextId() {
        return nextId++;
    }

    // Методы для Task
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void clearAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    // Методы для Epic
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void clearAllEpics() {
        epics.clear();
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Subtask subtask : new ArrayList<>(epic.getSubtasks())) {
                removeSubtaskById(subtask.getId());
            }
        }
        epics.remove(id);
    }

    // Методы для Subtask
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void clearAllSubtasks() {
        subtasks.clear();
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = subtask.getEpic();
            if (epic != null) {
                epic.removeSubtask(subtask);
            }
        }
        subtasks.remove(id);
    }

    // Дополнительные методы
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            return epic.getSubtasks();
        }
        return new ArrayList<>();
    }
}