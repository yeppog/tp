package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.address.logic.parser.CommandArgument.optionalMultiple;
import static seedu.address.logic.parser.CommandArgument.optionalSingle;
import static seedu.address.logic.parser.CommandArgument.requiredSingle;

import java.util.Set;

import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.IllegalPrefixException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Contact;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {
    @Override
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap;
        try {
            argMultimap = ArgumentTokenizer.tokenize(args,
                            requiredSingle(PREFIX_PREAMBLE),
                            optionalSingle(PREFIX_DESCRIPTION),
                            optionalSingle(PREFIX_TIMESTAMP),
                            optionalMultiple(PREFIX_TAG),
                            optionalMultiple(PREFIX_CONTACT)
                    );
        } catch (IllegalPrefixException e) {
            throw new ParseException(String.format(e.getMessage(), AddTaskCommand.MESSAGE_USAGE));
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        String title = argMultimap.getPreamble();
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).orElse(null);
        Timestamp timestamp = argMultimap.getValue(PREFIX_TIMESTAMP).map(ParserUtil::parseTimestamp).orElse(null);
        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<Contact> contacts = ParserUtil.parseContacts(argMultimap.getAllValues(PREFIX_CONTACT));

        return new AddTaskCommand(new Task(title, description, timestamp, tags, contacts));
    }
}
