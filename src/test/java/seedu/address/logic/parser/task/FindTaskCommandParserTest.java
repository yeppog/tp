package seedu.address.logic.parser.task;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.FindTaskCommand;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

class FindTaskCommandParserTest {
    private final FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parse_noArgument_success() {
        assertParseSuccess(parser, "     ",
                new FindTaskCommand(TaskContainsKeywordsPredicate.SHOW_ALL_TASKS_PREDICATE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindTaskCommand expectedFindTaskCommand =
                new FindTaskCommand(new TaskContainsKeywordsPredicate(Arrays.asList("Homework", "Meeting")));
        assertParseSuccess(parser, "Homework Meeting", expectedFindTaskCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Homework \n \t Meeting  \t", expectedFindTaskCommand);
    }
}
