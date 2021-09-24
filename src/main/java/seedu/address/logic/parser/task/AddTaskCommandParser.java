package seedu.address.logic.parser.task;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;

import java.util.Set;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

public class AddTaskCommandParser implements Parser<AddTaskCommand> {
    @Override
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_TIMESTAMP, PREFIX_TAG);

        String title = argMultimap.getPreamble();
        if (title.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).orElse(null);
        Timestamp timestamp = argMultimap.getValue(PREFIX_TIMESTAMP).map(ParserUtil::parseTimestamp).orElse(null);
        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new AddTaskCommand(new Task(title, description, timestamp, tags));
    }
}
