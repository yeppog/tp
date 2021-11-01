package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;

public class TaskListViewCell extends ListCell<Task> {
    private final Logger logger = LogsCenter.getLogger(TaskListViewCell.class);
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
            addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    event.consume();
                    showEditDialog(task);
                }
            });
            setOnMouseClicked((event) -> {
                // Double click
                if (event.getClickCount() == 2) {
                    event.consume();
                    showEditDialog(task);
                }
            });
        }
    }

    private void showEditDialog(Task task) {
        EditTaskDialog editTaskDialog = new EditTaskDialog(task);
        Optional<Task> editedTask = editTaskDialog.getDialog().showAndWait();
        editedTask.ifPresent(value -> taskEditor.updateTask(task, value));
    }
}
