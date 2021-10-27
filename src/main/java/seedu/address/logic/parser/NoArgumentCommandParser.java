package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.lang.reflect.InvocationTargetException;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.IllegalPrefixException;
import seedu.address.logic.parser.exceptions.ParseException;

public class NoArgumentCommandParser<C extends Command> implements Parser<C> {
    private final Class<C> commandClass;
    private final String commandUsage;

    /**
     * Creates a command parser that parses commands that do not accept any arguments.
     * Checks for validity of arguments by running provided arguments through {@link ArgumentTokenizer}.
     *
     * @param commandClass The class of the command to create
     * @param commandUsage The command usage text of the command
     */
    public NoArgumentCommandParser(Class<C> commandClass, String commandUsage) {
        this.commandClass = commandClass;
        this.commandUsage = commandUsage;
    }

    @Override
    public C parse(String args) throws ParseException {
        assertEmptyArguments(args);
        try {
            return commandClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                | InvocationTargetException
                | NoSuchMethodException
                | IllegalAccessException e) {
            e.printStackTrace();
            assert false : "Attempted to parse command without empty constructor";
            return null;
        }
    }

    private void assertEmptyArguments(String args) throws ParseException {
        try {
            ArgumentTokenizer.tokenize(args);
        } catch (IllegalPrefixException e) {
            throw new ParseException(String.format(e.getMessage(), commandUsage));
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, commandUsage));
        }
    }
}
