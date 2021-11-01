package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.CommandHistory;

public class UndoCommand implements Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the last executed command. \n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_UNDO_SUCCESS = "Successfully undid: ";
    public static final String MESSAGE_NOT_UNDONE = "Cannot undo any further.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        CommandHistory commandHistory = model.getCommandHistory();
        Optional<Command> commandToUndo = commandHistory.getHistoryCommand(false);
        if (commandToUndo.isPresent()) {
            Command command = commandToUndo.get();
            if (command instanceof UndoableCommand) {
                CommandResult result = ((UndoableCommand) command).undo(model);
                return new CommandResult(MESSAGE_UNDO_SUCCESS + result.getFeedbackToUser());
            } else {
                return new CommandResult("Undo not supported for this command, or cannot undo any further");
            }
        } else {
            return new CommandResult(MESSAGE_NOT_UNDONE);
        }
    }
}
