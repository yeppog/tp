package seedu.address.ui;

import javafx.scene.control.ListCell;
import seedu.address.model.task.Task;

public class TaskListViewCell extends ListCell<Task> {

    private final TaskListPanel.TaskEditor taskEditor;

    public TaskListViewCell(TaskListPanel.TaskEditor taskEditor) {
        this.taskEditor = taskEditor;
    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new TaskCard(task, getIndex() + 1, taskEditor).getRoot());
        }
    }
}
