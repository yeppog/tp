package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CommandArgument.filled;
import static seedu.address.logic.parser.CommandArgument.requiredSingle;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";

    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            COMMAND_WORD,
            "Finds all persons whose names contain any of the specified keywords (case-insensitive)\n"
                    + "and displays them as a list with index numbers.",
            requiredSingle(PREFIX_PREAMBLE, "KEYWORD [MORE_KEYWORDS]...")
    ).withExample(
            filled(PREFIX_PREAMBLE, "alice bob charlie")
    );

    private final NameContainsKeywordsPredicate predicate;

    private Predicate<? super Person> previousPredicate;

    /**
     * Constructor to initialise a FindCommand object.
     * @param predicate of the condition to filter the list.
     */
    public FindCommand(NameContainsKeywordsPredicate predicate) {
        super(true);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        super.canExecute();
        this.previousPredicate = model.getFilteredPersonPredicate();
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        super.canUndo();
        model.updateFilteredPersonList(this.previousPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
