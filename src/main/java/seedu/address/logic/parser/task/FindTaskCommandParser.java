package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.task.FindTaskCommand.COMMAND_SPECS;

import java.util.Arrays;

import seedu.address.logic.commands.task.FindTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentMultimapParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

public class FindTaskCommandParser extends ArgumentMultimapParser<FindTaskCommand> {
    public FindTaskCommandParser() {
        super(COMMAND_SPECS);
    }

    @Override
    public FindTaskCommand parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        String trimmedArgs = argMultimap.getPreamble();

        if (trimmedArgs.isEmpty()) {
            return new FindTaskCommand(TaskContainsKeywordsPredicate.SHOW_ALL_TASKS_PREDICATE);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTaskCommand(new TaskContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
