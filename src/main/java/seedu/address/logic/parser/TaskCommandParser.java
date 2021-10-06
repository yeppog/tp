package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.task.AddTaskCommandParser;

/**
 * Parses all task-related commands (those starting with "task") and returns a TaskCommand.
 */
public class TaskCommandParser implements Parser<TaskCommand> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    @Override
    public TaskCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);
        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommand(Index.fromOneBased(1));

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
