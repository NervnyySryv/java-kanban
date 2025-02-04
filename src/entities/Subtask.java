package entities;

import enums.Status;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String title, String description, int epicId) {
        super(id, title, description, Status.NEW);
        this.epicId = epicId;
    }

    public Subtask(String title, String description, int epicId) {
        super(title, description); // Используем уникальный ID
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}