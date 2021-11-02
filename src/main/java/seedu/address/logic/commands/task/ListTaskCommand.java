package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNDONE;
import static seedu.address.logic.parser.CommandArgument.filled;
import static seedu.address.logic.parser.CommandArgument.optionalMultiple;
import static seedu.address.logic.parser.CommandArgument.optionalSingle;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;
import seedu.address.model.task.filters.TaskFilter;

/**
 * Completes an existing task in the task list.
 */
public class ListTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "list";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Task list updated";
    public static final String MESSAGE_ONE_DONE_FILTER = "Tasks can only be filtered by done or undone at one time.";
    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            FULL_COMMAND_WORD,
            "List tasks matching the given search conditions.",
            optionalSingle(PREFIX_DONE),
            optionalSingle(PREFIX_UNDONE),
            optionalMultiple(PREFIX_TAG, "tag")
    ).withExample(
        filled(PREFIX_UNDONE, ""),
        filled(PREFIX_TAG, "important")
    );

    private final List<TaskFilter> taskFilters;

    private List<TaskFilter> previousFilters;

    public ListTaskCommand(List<TaskFilter> taskFilters) {
        this.taskFilters = taskFilters;
    }

    @Override
    protected CommandResult executeDo(Model model) throws CommandException {
        requireNonNull(model);
        this.previousFilters = model.getOldTaskFilters();
        model.setTaskFilters(taskFilters);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult executeUndo(Model model) throws CommandException {
        model.setTaskFilters(this.previousFilters);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || (o instanceof ListTaskCommand
                && taskFilters.equals(((ListTaskCommand) o).taskFilters));
    }

    @Override
    public int hashCode() {
        return taskFilters.hashCode();
    }
}

