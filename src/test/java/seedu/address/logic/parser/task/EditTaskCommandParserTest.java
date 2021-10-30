package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CAREER;
import static seedu.address.logic.commands.CommandTestUtil.TIMESTAMP_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CAREER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_REPORT;
import static seedu.address.logic.commands.task.EditTaskCommand.COMMAND_SPECS;
import static seedu.address.logic.commands.task.EditTaskCommand.EditTaskDescriptor;
import static seedu.address.logic.commands.task.EditTaskCommand.MESSAGE_NOT_EDITED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.EditTaskCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.InvalidCommandArgumentFormatException;
import seedu.address.logic.parser.exceptions.MissingCommandArgumentException;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {
    private static final String MESSAGE_MISSING_PREAMBLE =
            new MissingCommandArgumentException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PREAMBLE),
                    COMMAND_SPECS).getMessage();
    private static final String MESSAGE_INVALID_INDEX =
            new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PREAMBLE),
                    ParserUtil.MESSAGE_INVALID_INDEX, COMMAND_SPECS).getMessage();
    private static final String MESSAGE_NOT_EDITED =
            EditTaskCommand.MESSAGE_NOT_EDITED + "\n" + COMMAND_SPECS.getUsageMessage();

    final EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_emptyArguments_failure() {
        assertParseFailure(parser, "", MESSAGE_MISSING_PREAMBLE);
    }

    @Test
    public void parse_nonIntegerIndex_failure() {
        assertParseFailure(parser, "1.1", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "hello", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_nonPositiveIndex_failure() {
        assertParseFailure(parser, "-1", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "0", MESSAGE_INVALID_INDEX);
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
                .withTimestamp(VALID_TIMESTAMP_REPORT)
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
