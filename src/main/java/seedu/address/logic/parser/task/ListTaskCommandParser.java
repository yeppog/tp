package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.task.ListTaskCommand.COMMAND_SPECS;
import static seedu.address.logic.commands.task.ListTaskCommand.MESSAGE_ONE_DONE_FILTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNDONE;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.task.ListTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentMultimapParser;
import seedu.address.logic.parser.ArgumentParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.filters.TaskFilter;
import seedu.address.model.task.filters.TaskFilters;

public class ListTaskCommandParser extends ArgumentMultimapParser<ListTaskCommand> {
    public ListTaskCommandParser() {
        super(COMMAND_SPECS);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListTaskCommand parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        Set<Tag> tags = ArgumentParser.parseAllValues(PREFIX_TAG, ParserUtil::parseTags, argMultimap, COMMAND_SPECS);
        List<TaskFilter> taskFilters = tags.stream().map(TaskFilters.FILTER_TAG).collect(Collectors.toList());

        // Both done and undone flags are present
        if (argMultimap.getValue(PREFIX_DONE).isPresent()
                && argMultimap.getValue(PREFIX_UNDONE).isPresent()) {
            throw new ParseException(MESSAGE_ONE_DONE_FILTER + "\n" + COMMAND_SPECS.getUsageMessage());
        }

        if (argMultimap.getValue(PREFIX_DONE).isPresent()) {
            taskFilters.add(TaskFilters.FILTER_DONE);
        }

        if (argMultimap.getValue(PREFIX_UNDONE).isPresent()) {
            taskFilters.add(TaskFilters.FILTER_UNDONE);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(COMMAND_SPECS.getUsageMessage());
        }

        return new ListTaskCommand(taskFilters);
    }
}
