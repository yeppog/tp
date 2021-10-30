package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.task.DeleteTaskCommand.COMMAND_SPECS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.InvalidCommandArgumentFormatException;
import seedu.address.logic.parser.exceptions.MissingCommandArgumentException;

public class DeleteTaskCommandParserTest {
    private static final String MESSAGE_INVALID_INDEX =
            new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PREAMBLE),
            ParserUtil.MESSAGE_INVALID_INDEX, COMMAND_SPECS).getMessage();
    private static final String MESSAGE_MISSING_PREAMBLE =
            new MissingCommandArgumentException(
                    COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PREAMBLE), COMMAND_SPECS).getMessage();
    final DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_noIndex_failure() {
        assertParseFailure(parser, "", MESSAGE_MISSING_PREAMBLE);
    }

    @Test
    public void parse_nonIntegerIndex_failure() {
        assertParseFailure(parser, "1.1",
                MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "hello",
                MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_nonPositiveIndex_failure() {
        assertParseFailure(parser, "-1",
                MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "0",
                MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validIndex_success() {
        Index targetIndex = Index.fromOneBased(1);
        assertParseSuccess(parser, "1", new DeleteTaskCommand(targetIndex));
    }
}
