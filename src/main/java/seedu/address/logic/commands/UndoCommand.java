package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;
import seedu.address.storage.CommandHistory;

public class UndoCommand implements Command {
    public static final String COMMAND_WORD = "undo";
    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            COMMAND_WORD,
            "Undoes the last executed command."
    );
    public static final String MESSAGE_UNDO_SUCCESS = "Successfully undid: ";
    public static final String MESSAGE_NOT_UNDONE = "Cannot undo any further.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        CommandHistory commandHistory = model.getCommandHistory();
        Optional<UndoableCommand> commandToUndo = commandHistory.undo();
        if (commandToUndo.isPresent()) {
            CommandResult result = commandToUndo.get().undo(model);
            return new CommandResult(MESSAGE_UNDO_SUCCESS + result.getFeedbackToUser());
        } else {
            return new CommandResult(MESSAGE_NOT_UNDONE);
        }
    }
}
