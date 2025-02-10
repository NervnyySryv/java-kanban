package manager;

import entities.Epic;
import entities.Subtask;
import entities.Task;
import enums.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private int nextId;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.nextId = 0;
        this.historyManager = Managers.getDefaultHistory(); // Используем Managers для создания менеджера истории
    }

    private int generateNextId() {
        return nextId++;
    }

    // Методы для Task
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Задача с ID " + id + " не найдена.");
        } else {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void addTask(Task task) {
        task.setId(generateNextId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            Task existingTask = tasks.get(task.getId());
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setStatus(task.getStatus());
        } else {
            System.out.println("Задача с ID " + task.getId() + " не найдена.");
        }
    }

    @Override
    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Задача с ID " + id + " удалена.");
        } else {
            System.out.println("Задача с ID " + id + " не найдена.");
        }
    }

    // Методы для Epic
    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void clearAllEpics() {
        epics.clear();
        subtasks.clear(); // Очистка всех подзадач
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Эпик с ID " + id + " не найден.");
        } else {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateNextId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic existingEpic = epics.get(epic.getId());
            existingEpic.setTitle(epic.getTitle());
            existingEpic.setDescription(epic.getDescription());
            existingEpic.setStatus(epic.getStatus()); // Устанавливаем новый статус
            updateEpicStatus(epic.getId()); // Обновляем статус эпика на основе подзадач
        } else {
            System.out.println("Эпик с ID " + epic.getId() + " не найден.");
        }
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
            epic.getSubtaskIds().clear(); // Очистка списка подзадач в эпике
        }
        epics.remove(id);
        System.out.println("Эпик с ID " + id + " удален.");
    }

    // Методы для Subtask
    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void clearAllSubtasks() {
        subtasks.clear();
        // Очистка списка подзадач в каждом эпике
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
        }
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            System.out.println("Подзадача с ID " + id + " не найдена.");
        } else {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(generateNextId()); // Устанавливаем ID подзадачи
        subtasks.put(subtask.getId(), subtask);

        Integer epicId = subtask.getEpicId(); // Получаем ID эпика из подзадачи
        Epic epic = epics.get(epicId); // Получаем эпик по ID

        if (epic != null) {
            epic.addSubtaskId(subtask.getId()); // Добавляем ID подзадачи в эпик
            updateEpicStatus(epic.getId()); // Обновляем статус эпика
        } else {
            System.out.println("Эпик с ID " + epicId + " не найден.");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Subtask existingSubtask = subtasks.get(subtask.getId());
            existingSubtask.setTitle(subtask.getTitle());
            existingSubtask.setDescription(subtask.getDescription());
            existingSubtask.setStatus(subtask.getStatus());
            updateEpicStatus(subtask.getEpicId());
        } else {
            System.out.println("Подзадача с ID " + subtask.getId() + " не найдена.");
        }
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(epic.getId());
            }
            subtasks.remove(id);
            System.out.println("Подзадача с ID " + id + " удалена.");
        } else {
            System.out.println("Подзадача с ID " + id + " не найдена.");
        }
    }

    // Дополнительные методы
    @Override
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            List<Subtask> result = new ArrayList<>();
            for (Integer subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    result.add(subtask);
                }
            }
            return result;
        }
        return new ArrayList<>();
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        List<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask == null) {
                continue;
            }
            if (subtask.getStatus() != Status.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    // Метод для получения истории просмотров
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}