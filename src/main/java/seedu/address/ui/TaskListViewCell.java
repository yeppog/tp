package seedu.address.ui;

import java.util.Optional;
import java.util.function.Consumer;

import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.task.EditTaskCommand;
import seedu.address.model.task.Task;

public class TaskListViewCell extends ListCell<Task> {
    private final Consumer<? super Command> commandExecutor;

    public TaskListViewCell(Consumer<? super Command> commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new TaskCard(task, Index.fromZeroBased(getIndex()), commandExecutor).getRoot());
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
        editedTask.ifPresent(value ->
                commandExecutor.accept(new EditTaskCommand(Index.fromZeroBased(getIndex()),
                        EditTaskCommand.EditTaskDescriptor.from(value))));
    }
}
