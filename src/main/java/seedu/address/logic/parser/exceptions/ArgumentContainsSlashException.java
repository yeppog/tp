package seedu.address.logic.parser.exceptions;

import seedu.address.logic.parser.Prefix;

public class ArgumentContainsSlashException extends IllegalPrefixException {
    public ArgumentContainsSlashException(Prefix prefix) {
        super(prefix, "Cannot have / in argument value");
    }
}
