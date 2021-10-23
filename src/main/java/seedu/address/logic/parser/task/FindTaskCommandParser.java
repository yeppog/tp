package seedu.address.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.task.FindTaskCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

public class FindTaskCommandParser implements Parser<FindTaskCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput the input entered by the user
     * @throws ParseException if {@code userInput} does not conform to the expected format
     */
    @Override
    public FindTaskCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String trimmedArgs = userInput.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTaskCommand(new TaskContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
