package seedu.address.logic.parser;

import static seedu.address.logic.commands.AddCommand.COMMAND_SPECS;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.InvalidCommandArgumentFormatException;
import seedu.address.logic.parser.exceptions.MissingCommandArgumentException;
import seedu.address.logic.parser.exceptions.TooManyPrefixesException;
import seedu.address.logic.parser.exceptions.UnwantedPreambleException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - failure
        assertParseFailure(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new TooManyPrefixesException(CommandArgument.unknown(PREFIX_NAME),
                        COMMAND_SPECS).getMessage());

        // multiple phones - failure
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new TooManyPrefixesException(CommandArgument.unknown(PREFIX_PHONE),
                        COMMAND_SPECS).getMessage());

        // multiple emails - failure
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new TooManyPrefixesException(CommandArgument.unknown(PREFIX_EMAIL),
                        COMMAND_SPECS).getMessage());

        // multiple addresses - failure
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                        + ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new TooManyPrefixesException(CommandArgument.unknown(PREFIX_ADDRESS),
                        COMMAND_SPECS).getMessage());


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MissingCommandArgumentException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_NAME),
                        COMMAND_SPECS).getMessage());

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MissingCommandArgumentException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PHONE),
                        COMMAND_SPECS).getMessage());

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                new MissingCommandArgumentException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_EMAIL),
                        COMMAND_SPECS).getMessage());

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                new MissingCommandArgumentException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_ADDRESS),
                        COMMAND_SPECS).getMessage());

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                new MissingCommandArgumentException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_NAME),
                        COMMAND_SPECS).getMessage());
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new InvalidCommandArgumentFormatException(
                        COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_NAME),
                        Name.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage());

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new InvalidCommandArgumentFormatException(
                        COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PHONE),
                        Phone.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage());

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new InvalidCommandArgumentFormatException(
                        COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_EMAIL),
                        Email.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage());

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new InvalidCommandArgumentFormatException(
                        COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_ADDRESS),
                        Address.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage());

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_TAG),
                        Tag.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage());

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,

                new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_NAME),
                        Name.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage());

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new UnwantedPreambleException(PREAMBLE_NON_EMPTY, COMMAND_SPECS).getMessage());
    }
}
