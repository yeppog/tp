package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.task.AddTaskCommand.COMMAND_SPECS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.Set;

import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentMultimapParser;
import seedu.address.logic.parser.ArgumentParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Contact;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser extends ArgumentMultimapParser<AddTaskCommand> {
    public AddTaskCommandParser() {
        super(COMMAND_SPECS);
    }

    @Override
    public AddTaskCommand parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        String title = argMultimap.getPreamble();
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).orElse(null);
        Timestamp timestamp = ArgumentParser.parseOptionalValue(PREFIX_TIMESTAMP, ParserUtil::parseTimestamp,
                argMultimap, COMMAND_SPECS).orElse(null);
        Set<Tag> tags = ArgumentParser.parseAllValues(PREFIX_TAG, ParserUtil::parseTags, argMultimap, COMMAND_SPECS);
        Set<Contact> contacts = ArgumentParser.parseAllValues(PREFIX_CONTACT, ParserUtil::parseContacts, argMultimap,
                COMMAND_SPECS);

        return new AddTaskCommand(new Task(title, description, timestamp, tags, contacts));
    }
}
