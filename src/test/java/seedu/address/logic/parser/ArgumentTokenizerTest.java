package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CommandArgument.optionalMultiple;
import static seedu.address.logic.parser.CommandArgument.optionalSingle;
import static seedu.address.logic.parser.CommandArgument.requiredSingle;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ArgumentContainsSlashException;
import seedu.address.logic.parser.exceptions.MissingCommandArgumentException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.TooManyPrefixesException;
import seedu.address.logic.parser.exceptions.UnwantedCommandArgumentException;
import seedu.address.logic.parser.exceptions.UnwantedPreambleException;

public class ArgumentTokenizerTest {
    private static final Prefix prefix = new Prefix("p/");

    @Test
    public void tokenize_expectingOptionalPreambleValidArguments_success() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null,
                optionalSingle(PREFIX_PREAMBLE, "preambleName")
        ));
        try {
            tokenizer.tokenize("");
            tokenizer.tokenize("single");
            tokenizer.tokenize("multiple words");
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingOptionalMultipleValidArguments_success() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null,
                optionalMultiple(prefix)
        ));
        try {
            tokenizer.tokenize(prefix.getPrefix() + "");
            tokenizer.tokenize(prefix.getPrefix() + "single");
            tokenizer.tokenize(prefix.getPrefix() + "multiple words");
            tokenizer.tokenize(prefix.getPrefix() + "multiple"
                    + " " + prefix.getPrefix() + "arguments");
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingEmptyPreambleValidArguments_success() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null));
        try {
            tokenizer.tokenize("");
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingEmptyPreambleInvalidArguments_throwsUnwantedPrefixArguments() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null));
        try {
            tokenizer.tokenize("single");
            fail();
        } catch (UnwantedPreambleException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
        try {
            tokenizer.tokenize("multiple words");
            fail();
        } catch (UnwantedPreambleException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingOptionalSingleValidArguments_success() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null,
                optionalSingle(prefix)
        ));
        try {
            tokenizer.tokenize(prefix.getPrefix());
            tokenizer.tokenize(prefix.getPrefix() + " space");
            tokenizer.tokenize(prefix.getPrefix() + "single");
            tokenizer.tokenize(prefix.getPrefix() + "multiple words");
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingOptionalSingleInvalidArguments_throwsException() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null,
                optionalSingle(prefix)
        ));
        try {
            tokenizer.tokenize("");
        } catch (MissingCommandArgumentException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }

        try {
            tokenizer.tokenize(prefix.getPrefix() + "first " + prefix.getPrefix() + "second");
        } catch (TooManyPrefixesException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingRequiredSingleValidArguments_success() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null,
                requiredSingle(prefix)
        ));
        try {
            tokenizer.tokenize(prefix.getPrefix());
            tokenizer.tokenize(prefix.getPrefix() + "first");
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_expectingRequiredSingleInvalidArguments_throwsException() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null,
                requiredSingle(prefix)
        ));
        try {
            tokenizer.tokenize("");
        } catch (MissingCommandArgumentException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
        try {
            tokenizer.tokenize(prefix.getPrefix() + "first " + prefix.getPrefix() + "second");
        } catch (TooManyPrefixesException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_unexpectedArgument_throwUnwantedArgumentException() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null));
        try {
            tokenizer.tokenize(prefix.getPrefix());
        } catch (UnwantedCommandArgumentException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
        try {
            tokenizer.tokenize(prefix.getPrefix() + "first " + prefix.getPrefix() + "second");
        } catch (UnwantedCommandArgumentException ignored) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    public void tokenize_unexpectedArgument_throwArgumentContainsSlashException() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(new CommandSpecification(
                null, null,
                requiredSingle(prefix)
        ));

        try {
            tokenizer.tokenize(prefix.getPrefix() + "first/second");
        } catch (ArgumentContainsSlashException e) {
            // This is expected
        } catch (ParseException e) {
            fail(e);
        }
    }
}
