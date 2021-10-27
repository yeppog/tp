package seedu.address.logic.parser.task;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.EditTaskCommand;
import seedu.address.logic.commands.task.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Contact;

/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DESCRIPTION,
                        PREFIX_TIMESTAMP, PREFIX_TAG, PREFIX_CONTACT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE), pe);
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        argMultimap.getValue(PREFIX_TITLE)
                .map(ParserUtil::parseTitle)
                .ifPresent(editTaskDescriptor::setTitle);
        argMultimap.getValue(PREFIX_DESCRIPTION)
                .map(ParserUtil::parseDescription)
                .ifPresent(editTaskDescriptor::setDescription);

        if (argMultimap.getValue(PREFIX_TIMESTAMP).orElse(null) != null) {
            editTaskDescriptor.setTimestamp(ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_TIMESTAMP).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
                .ifPresent(editTaskDescriptor::setTags);
        parseContactsForEdit(argMultimap.getAllValues(PREFIX_CONTACT))
                .ifPresent(editTaskDescriptor::setContacts);

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> contacts} into a {@code Set<Contact>} if {@code contacts} is non-empty.
     * If {@code contacts} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Contact>} containing zero contacts.
     */
    private Optional<Set<Contact>> parseContactsForEdit(Collection<String> contacts) throws ParseException {
        assert contacts != null;

        if (contacts.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> contactSet = contacts.size() == 1
                && contacts.contains("")
                    ? Collections.emptySet()
                    : contacts;
        return Optional.of(ParserUtil.parseContacts(contactSet));
    }
}
