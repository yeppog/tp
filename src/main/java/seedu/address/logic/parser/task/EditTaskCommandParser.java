package seedu.address.logic.parser.task;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.task.EditTaskCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CommandArgument.optionalMultiple;
import static seedu.address.logic.parser.CommandArgument.optionalSingle;
import static seedu.address.logic.parser.CommandArgument.requiredSingle;

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
import seedu.address.logic.parser.exceptions.IllegalPrefixException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

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
        ArgumentMultimap argMultimap;
        try {
            argMultimap = ArgumentTokenizer.tokenize(args,
                    requiredSingle(PREFIX_PREAMBLE),
                    optionalSingle(PREFIX_TITLE),
                    optionalSingle(PREFIX_DESCRIPTION),
                    optionalSingle(PREFIX_TIMESTAMP),
                    optionalMultiple(PREFIX_TAG));
        } catch (IllegalPrefixException e) {
            throw new ParseException(String.format(e.getMessage(), MESSAGE_USAGE));
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

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
        argMultimap.getValue(PREFIX_TIMESTAMP)
                .map(ParserUtil::parseTimestamp)
                .ifPresent(editTaskDescriptor::setTimestamp);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
                .ifPresent(editTaskDescriptor::setTags);

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

}
