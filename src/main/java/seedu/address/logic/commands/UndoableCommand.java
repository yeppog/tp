package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public abstract class UndoableCommand implements Command {
    private boolean canExecute;

    public UndoableCommand() {
        this.canExecute = true;
    }

    /**
     * Tries to execute the undoable command with the given model, using
     * {@link UndoableCommand#executeDo}.
     *
     * If the command has been executed, a CommandException will be thrown.
     * Otherwise, the command will be marked as executed, and can be undone.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!this.canExecute) {
            throw new CommandException(Messages.MESSAGE_UNABLE_TO_EXECUTE);
        }
        CommandResult result = executeDo(model);
        // Set canExecute only when execute is successful
        this.canExecute = false;
        return result;
    }

    /**
     * Tries to undo the undoable command with the given model, using
     * {@link UndoableCommand#executeUndo}.
     *
     * If the command has been undone, a CommandException will be thrown.
     * Otherwise, the command will be marked as undone, and can be executed again.
     */
    public CommandResult undo(Model model) throws CommandException {
        if (this.canExecute) {
            throw new CommandException(Messages.MESSAGE_UNABLE_TO_UNDO);
        }
        CommandResult result = executeUndo(model);
        // Set canExecute only when undo is successful
        this.canExecute = true;
        return result;
    }

    protected abstract CommandResult executeDo(Model model) throws CommandException;
    protected abstract CommandResult executeUndo(Model model) throws CommandException;
}
