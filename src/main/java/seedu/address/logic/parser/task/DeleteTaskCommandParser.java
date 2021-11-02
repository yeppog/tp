package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.task.DeleteTaskCommand.COMMAND_SPECS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentMultimapParser;
import seedu.address.logic.parser.ArgumentParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteTaskCommandParser extends ArgumentMultimapParser<DeleteTaskCommand> {
    public DeleteTaskCommandParser() {
        super(COMMAND_SPECS);
    }

    @Override
    public DeleteTaskCommand parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        Index index = ArgumentParser.parseValue(PREFIX_PREAMBLE, ParserUtil::parseIndex, argMultimap, COMMAND_SPECS);
        return new DeleteTaskCommand(index);
    }
}
