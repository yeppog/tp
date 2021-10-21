package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

public class DeleteTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "delete";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Task deleted: %1$s";
    public static final String MESSAGE_USAGE = FULL_COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    private boolean canExecute = true;

    private Task deletedTask;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> taskList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } else if (!this.canExecute) {
            throw new CommandException(Messages.MESSAGE_UNABLE_TO_EXECUTE);
        }

        Task deletedTask = taskList.get(targetIndex.getZeroBased());
        this.deletedTask = deletedTask;
        model.deleteTask(deletedTask);

        this.canExecute = false;
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeleteTaskCommand that = (DeleteTaskCommand) o;
        return Objects.equals(targetIndex, that.targetIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex);
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        if (this.canExecute) {
            throw new CommandException(Messages.MESSAGE_UNABLE_TO_UNDO);
        }
        Predicate<? super Task> predicate = model.getFilteredTaskPredicate();
        model.insertTask(deletedTask, targetIndex.getZeroBased());
        this.canExecute = true;
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask));
    }
}
