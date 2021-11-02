package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.HelpCommand.COMMAND_SPECS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser implements Parser<Command> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, COMMAND_SPECS.getUsageMessage()));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new NoArgumentCommandParser<>(ClearCommand.class, ClearCommand.COMMAND_SPECS).parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new NoArgumentCommandParser<>(ListCommand.class, ListCommand.COMMAND_SPECS).parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new NoArgumentCommandParser<>(ExitCommand.class, ExitCommand.COMMAND_SPECS).parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new NoArgumentCommandParser<>(HelpCommand.class, COMMAND_SPECS).parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new NoArgumentCommandParser<>(UndoCommand.class, UndoCommand.COMMAND_SPECS).parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new NoArgumentCommandParser<>(RedoCommand.class, RedoCommand.COMMAND_SPECS).parse(arguments);

        // Every command starting with "task" delegated to TaskCommandParser
        case TaskCommand.COMMAND_WORD:
            return new TaskCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
