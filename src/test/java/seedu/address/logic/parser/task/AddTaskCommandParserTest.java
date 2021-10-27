package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CAREER;
import static seedu.address.logic.commands.CommandTestUtil.TIMESTAMP_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CAREER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_REPORT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.parser.exceptions.MissingPreambleException;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;


public class AddTaskCommandParserTest {
    private final AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    void parse_emptyArguments_failure() {
        assertParseFailure(parser, "",
                String.format(new MissingPreambleException().getMessage(), AddTaskCommand.MESSAGE_USAGE));
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
                .build();
        AddTaskCommand expectedCommand = new AddTaskCommand(expectedTask);
        assertParseSuccess(parser,
                VALID_TITLE_REPORT + DESCRIPTION_DESC_REPORT + TIMESTAMP_DESC_REPORT + TAG_DESC_CAREER,
                expectedCommand);
    }
}
