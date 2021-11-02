package seedu.address.logic.parser;

import static seedu.address.logic.commands.DeleteCommand.COMMAND_SPECS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser extends ArgumentMultimapParser<DeleteCommand> {
    public DeleteCommandParser() {
        super(COMMAND_SPECS);
    }

    @Override
    public DeleteCommand parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        Index index = ArgumentParser.parseValue(PREFIX_PREAMBLE, ParserUtil::parseIndex, argMultimap, COMMAND_SPECS);
        return new DeleteCommand(index);
    }

}
