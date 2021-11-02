package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.CommandSpecification;

public class UnwantedPreambleException extends IllegalCommandArgumentException {
    public UnwantedPreambleException(String preamble, CommandSpecification specs) {
        super(String.format("Unnecessary preamble with value \"%s\" present.", preamble), specs);
    }
}
