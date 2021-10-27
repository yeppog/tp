package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.Prefix;

public class TooManyPrefixesException extends IllegalPrefixException {
    public TooManyPrefixesException(Prefix prefix) {
        super(prefix, String.format("Too many arguments: %s!\n", prefix) + "%s");
    }
}
