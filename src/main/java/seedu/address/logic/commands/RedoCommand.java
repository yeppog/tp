package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;
import seedu.address.storage.CommandHistory;

public class RedoCommand implements Command {

    public static final String COMMAND_WORD = "redo";
    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            COMMAND_WORD,
            "Redoes the last executed command."
    );

    public static final String MESSAGE_REDO_SUCCESS = "Successfully redid: ";
    public static final String MESSAGE_NOT_UNDONE = "Cannot redo any further.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        CommandHistory commandHistory = model.getCommandHistory();
        Optional<UndoableCommand> commandToRedo = commandHistory.redo();
        if (commandToRedo.isPresent()) {
            CommandResult result = commandToRedo.get().execute(model);
            return new CommandResult(MESSAGE_REDO_SUCCESS + result.getFeedbackToUser());
        } else {
            return new CommandResult(MESSAGE_NOT_UNDONE);
        }
    }
}
