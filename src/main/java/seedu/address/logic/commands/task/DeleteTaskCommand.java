package seedu.address.logic.commands.task;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class DeleteTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "delete";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Task deleted: %1$s";
    public static final String MESSAGE_USAGE = FULL_COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "Delete task command not implemented yet";

    private final Index targetIndex;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
