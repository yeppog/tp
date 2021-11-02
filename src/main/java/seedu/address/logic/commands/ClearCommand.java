package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            COMMAND_WORD,
            "Clears the address book."
    );

    private ReadOnlyAddressBook oldAddressBook;

    @Override
    protected CommandResult executeDo(Model model) {
        requireNonNull(model);

        // Save a copy of the previous AddressBook
        this.oldAddressBook = new AddressBook(model.getAddressBook());

        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected CommandResult executeUndo(Model model) {
        model.setAddressBook(this.oldAddressBook);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
