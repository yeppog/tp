package seedu.address.logic.commands;

import java.util.Optional;
import java.util.function.Consumer;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.UserUndoStorage;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Successfully undid the previous command: ";

    public static final String MESSAGE_NOT_UNDONE = "Cannot undo any further.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        UserUndoStorage undoStack = model.getUserUndoStorage();
        Optional<Command> previousCommand = undoStack.popLastCommand();
        if (previousCommand.isPresent()) {
            CommandResult result = previousCommand.get().undo(model);
            System.out.println(result.getFeedbackToUser());
            return new CommandResult(MESSAGE_UNDO_SUCCESS + result.getFeedbackToUser());
        } else {
            return new CommandResult(MESSAGE_NOT_UNDONE);
        }
    }
}
