package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Purges all tasks currently in the task list
 */
public class PurgeTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "purge";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Tasks purged!";
    public static final String MESSAGE_NO_TASK_TO_PURGE = "There are no tasks to purge.";
    public static final String MESSAGE_USAGE = FULL_COMMAND_WORD
            + ": Purges all tasks in the displayed task list.\n"
            + "Example: " + FULL_COMMAND_WORD;

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

        if (taskList.size() == 0) {
            throw new CommandException(MESSAGE_NO_TASK_TO_PURGE);
        }

        // Create a copy of the list and deletes all tasks from the original
        Task[] tasksToDelete = taskList.toArray(new Task[0]);
        model.deleteAllInFilteredTaskList(tasksToDelete);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof PurgeTaskCommand;
    }
}
