package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CommandArgument.optionalSingle;
import static seedu.address.logic.parser.CommandArgument.requiredSingle;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.MissingPrefixException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.TooManyPrefixesException;
import seedu.address.logic.parser.exceptions.UnwantedPreambleException;
import seedu.address.logic.parser.exceptions.UnwantedPrefixException;

public class ArgumentTokenizerTest {
    private static final Prefix prefix = new Prefix("p/");

    @Test
    public void tokenize_expectingOptionalPreambleValidArguments_success() {
        try {
            ArgumentTokenizer.tokenize("",
                    optionalSingle(PREFIX_PREAMBLE));
            ArgumentTokenizer.tokenize("single",
                    optionalSingle(PREFIX_PREAMBLE));
            ArgumentTokenizer.tokenize("multiple words",
                    optionalSingle(PREFIX_PREAMBLE));
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingEmptyPreambleValidArguments_success() {
        try {
            ArgumentTokenizer.tokenize("");
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingEmptyPreambleInvalidArguments_throwsUnwantedPrefixArguments() {
        try {
            ArgumentTokenizer.tokenize("single");
            fail();
        } catch (UnwantedPreambleException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
        try {
            ArgumentTokenizer.tokenize("multiple words");
            fail();
        } catch (UnwantedPreambleException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingOptionalSingleValidArguments_success() {
        CommandArgument argument = optionalSingle(prefix);
        try {
            ArgumentTokenizer.tokenize(prefix.getPrefix(), argument);
            ArgumentTokenizer.tokenize(prefix.getPrefix() + " space", argument);
            ArgumentTokenizer.tokenize(prefix.getPrefix() + "single", argument);
            ArgumentTokenizer.tokenize(prefix.getPrefix() + "multiple words", argument);
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingOptionalSingleInvalidArguments_throwsException() {
        CommandArgument argument = optionalSingle(prefix);
        try {
            ArgumentTokenizer.tokenize("", argument);
        } catch (MissingPrefixException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }

        try {
            ArgumentTokenizer.tokenize(prefix.getPrefix() + "first " + prefix.getPrefix() + "second", argument);
        } catch (TooManyPrefixesException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingRequiredSingleValidArguments_success() {
        CommandArgument argument = requiredSingle(prefix);
        try {
            ArgumentTokenizer.tokenize(prefix.getPrefix(), argument);
            ArgumentTokenizer.tokenize(prefix.getPrefix() + "first", argument);
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingRequiredSingleInvalidArguments_throwsException() {
        CommandArgument argument = requiredSingle(prefix);
        try {
            ArgumentTokenizer.tokenize("", argument);
        } catch (MissingPrefixException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
        try {
            ArgumentTokenizer.tokenize(prefix.getPrefix() + "first " + prefix.getPrefix() + "second", argument);
        } catch (TooManyPrefixesException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_unexpectedArgument_throwUnwantedArgumentException() {
        try {
            ArgumentTokenizer.tokenize(prefix.getPrefix());
        } catch (UnwantedPrefixException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
        try {
            ArgumentTokenizer.tokenize(prefix.getPrefix() + "first " + prefix.getPrefix() + "second");
        } catch (UnwantedPrefixException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
    }
}
