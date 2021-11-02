package seedu.address.logic.commands.task;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.address.logic.parser.CommandArgument.filled;
import static seedu.address.logic.parser.CommandArgument.optionalMultiple;
import static seedu.address.logic.parser.CommandArgument.optionalSingle;
import static seedu.address.logic.parser.CommandArgument.requiredSingle;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Adds a task to the task list.
 */
public class AddTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "add";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            FULL_COMMAND_WORD,
            "Add a task with the given title field, and optionally a description, timestamp, tags and contacts.",
            requiredSingle(PREFIX_PREAMBLE, "title"),
            optionalSingle(PREFIX_DESCRIPTION, "description"),
            optionalSingle(PREFIX_TIMESTAMP, "timestamp"),
            optionalMultiple(PREFIX_TAG, "tag"),
            optionalMultiple(PREFIX_CONTACT, "contact_name")
    ).withExample(
            filled(PREFIX_PREAMBLE, "Do homework"),
            filled(PREFIX_DESCRIPTION, "Physics assignment"),
            filled(PREFIX_TIMESTAMP, "25-12-2020")
    );

    private final Task task;

    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    protected CommandResult executeDo(Model model) throws CommandException {
        model.addTask(task);
        return new CommandResult(String.format(MESSAGE_SUCCESS, task));
    }

    @Override
    protected CommandResult executeUndo(Model model) throws CommandException {
        model.deleteTaskAtLastIndex();
        return new CommandResult(String.format(MESSAGE_SUCCESS, task));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || ((other instanceof AddTaskCommand)
                && task.deepEquals(((AddTaskCommand) other).task));
    }

    @Override
    public int hashCode() {
        return task.hashCode();
    }
}

