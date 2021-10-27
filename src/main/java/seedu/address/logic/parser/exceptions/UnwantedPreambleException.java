package seedu.address.logic.parser.exceptions;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;

public class UnwantedPreambleException extends IllegalPrefixException {
    public UnwantedPreambleException(String preamble) {
        super(PREFIX_PREAMBLE, String.format("Unnecessary preamble present: %s\n", preamble) + "%s");
    }
}
