package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CAREER;
import static seedu.address.logic.commands.CommandTestUtil.TIMESTAMP_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CAREER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_REPORT;
import static seedu.address.logic.commands.task.AddTaskCommand.COMMAND_SPECS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.parser.exceptions.MissingCommandArgumentException;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;


public class AddTaskCommandParserTest {
    private static final String MESSAGE_MISSING_PREAMBLE =
            new MissingCommandArgumentException(
                    COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PREAMBLE), COMMAND_SPECS).getMessage();
    private final AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    void parse_emptyArguments_failure() {
        assertParseFailure(parser, "", MESSAGE_MISSING_PREAMBLE);
    }

    @Test
    void parse_onlyTitle_success() {
        String title = "Task title";
        Task expectedTask = new TaskBuilder()
                .withTitle(title)
                .withDescription(null)
                .withDone(false)
                .withTimestamp(null)
                .withTags()
                .build();
        AddTaskCommand expectedCommand = new AddTaskCommand(expectedTask);
        assertParseSuccess(parser, title, expectedCommand);
    }

    @Test
    void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder()
                .withTitle(VALID_TITLE_REPORT)
                .withDescription(VALID_DESCRIPTION_REPORT)
                .withTimestamp(VALID_TIMESTAMP_REPORT)
                .withTags(VALID_TAG_CAREER)
                .withContacts(VALID_NAME_AMY, VALID_NAME_BOB)
                .build();
        AddTaskCommand expectedCommand = new AddTaskCommand(expectedTask);
        assertParseSuccess(parser,
                VALID_TITLE_REPORT + DESCRIPTION_DESC_REPORT + TIMESTAMP_DESC_REPORT + TAG_DESC_CAREER
                + CONTACT_DESC_AMY + CONTACT_DESC_BOB,
                expectedCommand);
    }
}
