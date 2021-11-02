package seedu.address.logic.parser;

import static seedu.address.logic.commands.EditCommand.COMMAND_SPECS;
import static seedu.address.logic.commands.EditCommand.MESSAGE_NOT_EDITED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser extends ArgumentMultimapParser<EditCommand> {
    public EditCommandParser() {
        super(COMMAND_SPECS);
    }

    @Override
    public EditCommand parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        Index index = ArgumentParser.parseValue(PREFIX_PREAMBLE, ParserUtil::parseIndex, argMultimap, COMMAND_SPECS);
        Optional<Name> name = ArgumentParser.parseOptionalValue(PREFIX_NAME, ParserUtil::parseName, argMultimap,
                COMMAND_SPECS);
        Optional<Phone> phone = ArgumentParser.parseOptionalValue(PREFIX_PHONE, ParserUtil::parsePhone, argMultimap,
                COMMAND_SPECS);
        Optional<Email> email = ArgumentParser.parseOptionalValue(PREFIX_EMAIL, ParserUtil::parseEmail, argMultimap,
                COMMAND_SPECS);
        Optional<Address> address = ArgumentParser.parseOptionalValue(PREFIX_ADDRESS, ParserUtil::parseAddress,
                argMultimap, COMMAND_SPECS);
        Optional<Set<Tag>> tags = ArgumentParser.parseAllValues(PREFIX_TAG, EditCommandParser::parseTagsForEdit,
                argMultimap, COMMAND_SPECS);

        name.ifPresent(editPersonDescriptor::setName);
        phone.ifPresent(editPersonDescriptor::setPhone);
        email.ifPresent(editPersonDescriptor::setEmail);
        address.ifPresent(editPersonDescriptor::setAddress);
        tags.ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED + "\n" + COMMAND_SPECS.getUsageMessage());
        }

        return new EditCommand(index, editPersonDescriptor);
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

}
