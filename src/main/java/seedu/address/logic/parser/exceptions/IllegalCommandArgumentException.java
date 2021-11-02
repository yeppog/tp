package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.CommandSpecification;

public abstract class IllegalCommandArgumentException extends ParseException {
    protected IllegalCommandArgumentException(String argumentErrorMessage, CommandSpecification specs) {
        super(argumentErrorMessage + "\n" + specs.getUsageMessage());
    }
}
