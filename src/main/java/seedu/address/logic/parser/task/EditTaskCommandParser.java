package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.task.EditTaskCommand.COMMAND_SPECS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
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
import seedu.address.logic.parser.ArgumentMultimapParser;
import seedu.address.logic.parser.ArgumentParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Contact;
import seedu.address.model.task.Timestamp;

/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser extends ArgumentMultimapParser<EditTaskCommand> {

    public EditTaskCommandParser() {
        super(COMMAND_SPECS);
    }

    @Override
    public EditTaskCommand parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        Index index = ArgumentParser.parseValue(PREFIX_PREAMBLE, ParserUtil::parseIndex, argMultimap, COMMAND_SPECS);
        Optional<String> title = ArgumentParser.parseOptionalValue(PREFIX_TITLE, ParserUtil::parseTitle, argMultimap,
                COMMAND_SPECS);
        Optional<String> description = ArgumentParser.parseOptionalValue(PREFIX_DESCRIPTION,
                ParserUtil::parseDescription, argMultimap, COMMAND_SPECS);
        Optional<Timestamp> timestamp = ArgumentParser.parseOptionalValue(PREFIX_TIMESTAMP,
                ParserUtil::parseTimestamp, argMultimap, COMMAND_SPECS);
        Optional<Set<Tag>> tags = ArgumentParser.parseAllValues(PREFIX_TAG, EditTaskCommandParser::parseTagsForEdit,
                argMultimap, COMMAND_SPECS);
        Optional<Set<Contact>> contacts = ArgumentParser.parseAllValues(PREFIX_CONTACT,
                EditTaskCommandParser::parseContactsForEdit, argMultimap, COMMAND_SPECS);

        title.ifPresent(editTaskDescriptor::setTitle);
        description.ifPresent(editTaskDescriptor::setDescription);
        timestamp.ifPresent(editTaskDescriptor::setTimestamp);
        tags.ifPresent(editTaskDescriptor::setTags);
        contacts.ifPresent(editTaskDescriptor::setContacts);

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED + "\n" + COMMAND_SPECS.getUsageMessage());
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private static Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
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
    private static Optional<Set<Contact>> parseContactsForEdit(Collection<String> contacts) throws ParseException {
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
