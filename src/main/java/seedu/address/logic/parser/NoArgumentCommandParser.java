package seedu.address.logic.parser;

import java.lang.reflect.InvocationTargetException;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public class NoArgumentCommandParser<C extends Command> extends ArgumentMultimapParser<C> {
    private final Class<C> commandClass;

    /**
     * Creates a command parser that parses commands that do not accept any arguments.
     * Checks for validity of arguments by running provided arguments through {@link ArgumentTokenizer}.
     *
     * @param specs The specifications of the command
     */
    public NoArgumentCommandParser(Class<C> commandClass, CommandSpecification specs) {
        super(specs);
        this.commandClass = commandClass;
    }

    @Override
    public C parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
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
}
