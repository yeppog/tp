package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.CommandSpecification;

public class MissingCommandArgumentException extends IllegalCommandArgumentException {
    public MissingCommandArgumentException(CommandArgument argument, CommandSpecification specs) {
        super(String.format("Missing required argument: %s.", argument), specs);
    }
}
