package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandArgument.optionalMultiple;
import static seedu.address.logic.parser.CommandArgument.requiredSingle;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            COMMAND_WORD,
            "Adds a person to the address book.",
            requiredSingle(PREFIX_NAME, "name"),
            requiredSingle(PREFIX_PHONE, "phone"),
            requiredSingle(PREFIX_EMAIL, "email"),
            requiredSingle(PREFIX_ADDRESS, "address"),
            optionalMultiple(PREFIX_TAG, "tag")
    ).withExample(
            CommandArgument.filled(PREFIX_NAME, "John Doe"),
            CommandArgument.filled(PREFIX_PHONE, "98765432"),
            CommandArgument.filled(PREFIX_EMAIL, "johnd@example.com"),
            CommandArgument.filled(PREFIX_ADDRESS, "311, Clementi Ave 2, #02-25"),
            CommandArgument.filled(PREFIX_TAG, "friends"),
            CommandArgument.filled(PREFIX_TAG, "owesMoney")
    );

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    protected CommandResult executeDo(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    protected CommandResult executeUndo(Model model) {
        model.deletePerson(this.toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
