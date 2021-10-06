package seedu.address.logic.commands.task;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Adds a task to the task list.
 */
public class AddTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_USAGE = "task " + COMMAND_WORD
            + ": Adds a task with a given title to the task list.\n"
            + "Parameters: title (must be a non-empty string) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TIMESTAMP + "TIMESTAMP] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " Do homework "
            + PREFIX_DESCRIPTION + "Physics assignment "
            + PREFIX_TIMESTAMP + "25/12/2020";

    private final Task task;

    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.addTask(task);
        return new CommandResult(String.format(MESSAGE_SUCCESS, task));
    }
}
