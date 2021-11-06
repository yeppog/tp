package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CommandArgument.filled;
import static seedu.address.logic.parser.CommandArgument.optionalSingle;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;
import seedu.address.model.task.TaskContainsKeywordsPredicate;
import seedu.address.model.task.filters.KeywordTaskFilter;
import seedu.address.model.task.filters.TaskFilter;
import seedu.address.model.task.filters.TaskFilters;

public class FindTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "find";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;

    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            FULL_COMMAND_WORD,
            "Finds all tasks whose names or description contain any of the specified keywords (case-insensitive)\n"
                    + "and displays them as a list with index numbers.",
            optionalSingle(PREFIX_PREAMBLE, "KEYWORD [MORE_KEYWORDS]...")
    ).withExample(
            filled(PREFIX_PREAMBLE, "CS2103 CS2106 PC3130")
    );

    private final TaskContainsKeywordsPredicate predicate;
    private TaskFilter prevPredicate;
    private String displayedString;

    /**
     * Contructor for command. Sets previous predicate to null.
     *
     * @param predicate Predicate to filter the task with.
     */
    public FindTaskCommand(TaskContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        prevPredicate = null;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    protected CommandResult executeDo(Model model) throws CommandException {
        requireNonNull(model);

        model.getSelectedTaskFilters().stream()
                .filter(filter -> filter instanceof KeywordTaskFilter)
                .findFirst().ifPresent(filter -> {
                    prevPredicate = filter;
                    model.removeTaskFilter(filter);
                });


        if (!predicate.equals(TaskContainsKeywordsPredicate.SHOW_ALL_TASKS_PREDICATE)) {
            model.addTaskFilter(TaskFilters.FILTER_KEYWORDS.apply(predicate));
        }

        displayedString = String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW,
                model.getFilteredTaskList().size());
        return new CommandResult(displayedString);
    }

    @Override
    protected CommandResult executeUndo(Model model) throws CommandException {
        model.getSelectedTaskFilters().stream()
                .filter(filter -> filter instanceof KeywordTaskFilter)
                .findFirst().ifPresent(model::removeTaskFilter);

        Optional.ofNullable(prevPredicate).ifPresent(model::addTaskFilter);

        return new CommandResult(displayedString);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTaskCommand)) {
            return false;
        }

        // state check
        FindTaskCommand e = (FindTaskCommand) other;
        boolean isPrevPredicateEquals = Optional.ofNullable(prevPredicate)
                .equals(Optional.ofNullable(e.prevPredicate));
        return predicate.equals(e.predicate) && isPrevPredicateEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(predicate, prevPredicate);
    }
}
