package seedu.address.logic.parser;

import static seedu.address.logic.commands.AddCommand.COMMAND_SPECS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser extends ArgumentMultimapParser<AddCommand> {

    public AddCommandParser() {
        super(COMMAND_SPECS);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        Name name = ArgumentParser.parseValue(PREFIX_NAME, ParserUtil::parseName, argMultimap, COMMAND_SPECS);
        Phone phone = ArgumentParser.parseValue(PREFIX_PHONE, ParserUtil::parsePhone, argMultimap, COMMAND_SPECS);
        Email email = ArgumentParser.parseValue(PREFIX_EMAIL, ParserUtil::parseEmail, argMultimap, COMMAND_SPECS);
        Address address = ArgumentParser.parseValue(PREFIX_ADDRESS, ParserUtil::parseAddress, argMultimap,
                COMMAND_SPECS);
        Set<Tag> tagList = ArgumentParser.parseAllValues(PREFIX_TAG, ParserUtil::parseTags, argMultimap, COMMAND_SPECS);

        Person person = new Person(name, phone, email, address, tagList);

        return new AddCommand(person);
    }
}
