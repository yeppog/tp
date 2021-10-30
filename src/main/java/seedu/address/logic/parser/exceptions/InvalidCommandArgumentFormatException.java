package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.CommandSpecification;

public class InvalidCommandArgumentFormatException extends IllegalCommandArgumentException {
    /**
     * Creates an exception representing the case where an argument to a given command has an invalid format.
     *
     * @param argument The argument with an invalid format
     * @param argumentErrorMessage The error message associated with the parsing error
     * @param specs The specifications of the command
     */
    public InvalidCommandArgumentFormatException(
            CommandArgument argument,
            String argumentErrorMessage,
            CommandSpecification specs
    ) {
        super(String.format("Error in format of %s: %s", argument.getName(), argumentErrorMessage), specs);
    }
}
