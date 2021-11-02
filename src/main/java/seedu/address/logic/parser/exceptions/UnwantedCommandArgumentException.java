package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.CommandSpecification;

public class UnwantedCommandArgumentException extends IllegalCommandArgumentException {
    public UnwantedCommandArgumentException(CommandArgument argument, CommandSpecification specs) {
        super(String.format("Unnecessary prefix %s present.", argument.getPrefix()), specs);
    }
}
