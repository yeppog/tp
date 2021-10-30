package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            COMMAND_WORD,
            "Clears the address book."
    );

    private ReadOnlyAddressBook oldAddressBook;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!this.canExecute) {
            throw new CommandException(Messages.MESSAGE_UNABLE_TO_EXECUTE);
        }
        requireNonNull(model);
        this.oldAddressBook = model.getAddressBook();
        model.setAddressBook(new AddressBook());
        this.canExecute = false;
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        super.canUndo();
        model.setAddressBook(this.oldAddressBook);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
