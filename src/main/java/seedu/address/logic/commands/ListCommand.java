package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "list";
    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            COMMAND_WORD,
            "Lists all persons in the address book."
    );

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    private Predicate<? super Person> previousPredicate;

    @Override
    protected CommandResult executeDo(Model model) {
        requireNonNull(model);
        this.previousPredicate = model.getFilteredPersonPredicate();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected CommandResult executeUndo(Model model) {
        model.updateFilteredPersonList(this.previousPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
