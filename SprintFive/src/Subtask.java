public class Subtask extends Task {
    private Epic epic;

    public Subtask(int id, String title, String description, Status status, Epic epic) {
        super(id, title, description, status);
        this.epic = epic;
        epic.addSubtask(this);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return getId() == subtask.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }
}