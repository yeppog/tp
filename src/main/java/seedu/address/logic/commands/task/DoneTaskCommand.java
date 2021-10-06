package seedu.address.logic.commands.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Completes an existing task in the task list.
 */
public class DoneTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "done";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Task completed: %1$s";
    public static final String MESSAGE_ALREADY_DONE = "Task has already been completed: %1$s";
    public static final String MESSAGE_USAGE = FULL_COMMAND_WORD
            + ": Completes an existing task in the task list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + FULL_COMMAND_WORD + " 1";

    private final Index index;

    public DoneTaskCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        Task task;
        try {
            task = model.getTaskAtIndex(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (task.getIsDone()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_DONE, task));
        }

        Task completedTask = new Task(task.getTitle(), task.getDescription(), task.getTimestamp(),
                task.getTags(), !task.getIsDone());
        model.setTask(index.getZeroBased(), completedTask);

        return new CommandResult(String.format(MESSAGE_SUCCESS, completedTask));
    }
}
