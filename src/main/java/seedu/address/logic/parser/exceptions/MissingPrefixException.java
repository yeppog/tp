package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.Prefix;

public class MissingPrefixException extends IllegalPrefixException {
    public MissingPrefixException(Prefix prefix) {
        super(prefix, String.format("Missing required prefix: %s\n", prefix) + "%s");
    }
}
