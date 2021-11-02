package seedu.address.logic.parser.exceptions;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;

import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.CommandSpecification;

public class TooManyPrefixesException extends IllegalCommandArgumentException {

    /**
     * Creates an exception representing the case where too many prefixes are supplied as arguments to a command.
     * @param argument The argument with too many prefixes
     * @param specs The specifications of the command
     */
    public TooManyPrefixesException(CommandArgument argument, CommandSpecification specs) {
        super(String.format("Too many arguments for the prefix %s.", argument.getPrefix()), specs);
        assert !argument.getPrefix().equals(PREFIX_PREAMBLE);
    }
}
