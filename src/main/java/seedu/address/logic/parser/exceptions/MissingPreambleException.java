package seedu.address.logic.parser.exceptions;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;

public class MissingPreambleException extends IllegalPrefixException {
    public MissingPreambleException() {
        super(PREFIX_PREAMBLE, "Missing required preamble!\n%s");
    }
}
