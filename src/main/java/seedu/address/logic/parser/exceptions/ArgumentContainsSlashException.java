package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.CommandSpecification;

public class ArgumentContainsSlashException extends IllegalCommandArgumentException {
    public ArgumentContainsSlashException(CommandArgument argument, CommandSpecification specs) {
        super(String.format("Cannot have / in the value of %s.", argument.getName()), specs);
    }
}
