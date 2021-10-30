package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.EditCommand.COMMAND_SPECS;
import static seedu.address.logic.commands.EditCommand.MESSAGE_NOT_EDITED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.InvalidCommandArgumentFormatException;
import seedu.address.logic.parser.exceptions.MissingCommandArgumentException;
import seedu.address.logic.parser.exceptions.TooManyPrefixesException;
import seedu.address.logic.parser.exceptions.UnwantedCommandArgumentException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {
    private static final String MESSAGE_INVALID_NAME =
            new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_NAME),
                    Name.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage();
    private static final String MESSAGE_INVALID_PHONE =
            new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PHONE),
                    Phone.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage();
    private static final String MESSAGE_INVALID_EMAIL =
            new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_EMAIL),
                    Email.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage();
    private static final String MESSAGE_INVALID_TAG =
            new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_TAG),
                    Tag.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage();
    private static final String MESSAGE_INVALID_ADDRESS =
            new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_ADDRESS),
            Address.MESSAGE_CONSTRAINTS, COMMAND_SPECS).getMessage();
    private static final String MESSAGE_INVALID_INDEX =
            new InvalidCommandArgumentFormatException(COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PREAMBLE),
                    ParserUtil.MESSAGE_INVALID_INDEX, COMMAND_SPECS).getMessage();
    private static final String MESSAGE_NOT_EDITED =
            EditCommand.MESSAGE_NOT_EDITED + "\n" + COMMAND_SPECS.getUsageMessage();
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private final EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_INDEX);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "",
                new MissingCommandArgumentException(
                        COMMAND_SPECS.getCommandArgumentWithPrefix(PREFIX_PREAMBLE), COMMAND_SPECS).getMessage());
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_INDEX);

        // invalid prefix
        assertParseFailure(parser, "1 i/ string",
                new UnwantedCommandArgumentException(
                        CommandArgument.unknown(new Prefix("i/")), COMMAND_SPECS).getMessage());
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, MESSAGE_INVALID_NAME);
        // invalid phone
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, MESSAGE_INVALID_PHONE);
        // invalid email
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, MESSAGE_INVALID_EMAIL);
        // invalid address
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, MESSAGE_INVALID_ADDRESS);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, MESSAGE_INVALID_TAG);
        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, MESSAGE_INVALID_PHONE);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                MESSAGE_INVALID_TAG);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                MESSAGE_INVALID_TAG);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                MESSAGE_INVALID_TAG);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                MESSAGE_INVALID_NAME);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_repeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                new TooManyPrefixesException(CommandArgument.unknown(PREFIX_PHONE), COMMAND_SPECS).getMessage());
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
