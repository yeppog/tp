package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CAREER;
import static seedu.address.logic.commands.CommandTestUtil.TIMESTAMP_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CAREER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_REPORT;
import static seedu.address.logic.commands.task.EditTaskCommand.EditTaskDescriptor;
import static seedu.address.logic.commands.task.EditTaskCommand.MESSAGE_NOT_EDITED;
import static seedu.address.logic.commands.task.EditTaskCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.task.EditTaskCommand;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {
    final EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_emptyArguments_failure() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_nonIntegerIndex_failure() {
        assertParseFailure(parser, "1.1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "hello",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_nonPositiveIndex_failure() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_noFieldsEdited_failure() {
        assertParseFailure(parser, "1", MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_titleEdited_success() {
        Index targetIndex = Index.fromOneBased(1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTitle(VALID_TITLE_REPORT)
                .build();

        String targetIndexString = Integer.toString(targetIndex.getOneBased());

        assertParseSuccess(parser, targetIndexString + TITLE_DESC_REPORT, new EditTaskCommand(targetIndex, descriptor));
    }

    @Test
    public void parse_allFieldsEdited_success() {
        Index targetIndex = Index.fromOneBased(1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTitle(VALID_TITLE_REPORT)
                .withDescription(VALID_DESCRIPTION_REPORT)
                .withTimestamp(CommandTestUtil.getValidTimestampReport())
                .withTags(VALID_TAG_CAREER)
                .build();

        String targetIndexString = Integer.toString(targetIndex.getOneBased());

        assertParseSuccess(parser,
                targetIndexString
                        + TITLE_DESC_REPORT
                        + DESCRIPTION_DESC_REPORT
                        + TIMESTAMP_DESC_REPORT
                        + TAG_DESC_CAREER,
                new EditTaskCommand(targetIndex, descriptor));
    }
}
