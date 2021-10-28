package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.Prefix;

public class IllegalPrefixException extends ParseException {
    private final Prefix prefix;

    protected IllegalPrefixException(Prefix prefix, String message) {
        super(message);
        this.prefix = prefix;
    }

    public Prefix getPrefix() {
        return prefix;
    }
}
