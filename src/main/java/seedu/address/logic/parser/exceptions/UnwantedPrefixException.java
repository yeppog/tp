package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.Prefix;

public class UnwantedPrefixException extends IllegalPrefixException {
    public UnwantedPrefixException(Prefix prefix) {
        super(prefix, String.format("Unnecessary prefix present: %s\n", prefix) + "%s");
    }
}
