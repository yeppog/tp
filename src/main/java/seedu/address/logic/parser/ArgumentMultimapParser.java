package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public abstract class ArgumentMultimapParser<T extends Command> implements Parser<T> {
    private final ArgumentTokenizer argumentTokenizer;

    public ArgumentMultimapParser(CommandSpecification specs) {
        argumentTokenizer = new ArgumentTokenizer(specs);
    }

    @Override
    public T parse(String userInput) throws ParseException {
        return parseArgumentMultimap(argumentTokenizer.tokenize(userInput));
    }

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform to the expected format
     */
    public abstract T parseArgumentMultimap(ArgumentMultimap argumentMultimap) throws ParseException;
}
