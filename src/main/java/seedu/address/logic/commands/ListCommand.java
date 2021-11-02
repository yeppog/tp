package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            COMMAND_WORD,
            "Lists all persons in the address book."
    );

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    private Predicate<? super Person> previousPredicate;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        super.canExecute();
        this.previousPredicate = model.getFilteredPersonPredicate();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        this.canExecute = false;
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        super.canUndo();
        model.updateFilteredPersonList(this.previousPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
