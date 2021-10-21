package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Deletes an existing task from the task list.
 */
public class DeleteTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "delete";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Deleted task: %1$s";
    public static final String MESSAGE_USAGE = FULL_COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + FULL_COMMAND_WORD + " 1";

    private final Index targetIndex;

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
        }

        Task deletedTask = taskList.get(targetIndex.getZeroBased());
        model.deleteTask(deletedTask);

        return new CommandResult(String.format(MESSAGE_SUCCESS, deletedTask));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTaskCommand) other).targetIndex));
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode();
    }
}
