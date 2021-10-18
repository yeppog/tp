package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.DeleteTaskCommand;

public class DeleteTaskCommandParserTest {
    final DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_noIndex_failure() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonIntegerIndex_failure() {
        assertParseFailure(parser, "1.1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "hello",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonPositiveIndex_failure() {
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validIndex_success() {
        Index targetIndex = Index.fromOneBased(1);
        assertParseSuccess(parser, "1", new DeleteTaskCommand(targetIndex));
    }
}
