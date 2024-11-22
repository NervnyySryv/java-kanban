import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(int id, String title, String description, Status status) {
        super(id, title, description, status);
        this.subtasks = new ArrayList<>();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateStatus();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateStatus();
    }

    private void updateStatus() {
        if (subtasks.isEmpty()) {
            setStatus(Status.NEW);
        } else {
            boolean allNew = true;
            boolean allDone = true;
            for (Subtask subtask : subtasks) {
                if (subtask.getStatus() != Status.NEW) {
                    allNew = false;
                }
                if (subtask.getStatus() != Status.DONE) {
                    allDone = false;
                }
            }
            if (allNew) {
                setStatus(Status.NEW);
            } else if (allDone) {
                setStatus(Status.DONE);
            } else {
                setStatus(Status.IN_PROGRESS);
            }
        }
    }
}