package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.task.DoneTaskCommand.COMMAND_SPECS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.DoneTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentMultimapParser;
import seedu.address.logic.parser.ArgumentParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class DoneTaskCommandParser extends ArgumentMultimapParser<DoneTaskCommand> {
    public DoneTaskCommandParser() {
        super(COMMAND_SPECS);
    }

    @Override
    public DoneTaskCommand parseArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        Index index = ArgumentParser.parseValue(PREFIX_PREAMBLE, ParserUtil::parseIndex, argMultimap, COMMAND_SPECS);
        return new DoneTaskCommand(index);
    }
}
