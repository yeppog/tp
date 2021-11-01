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
     * Checks if the current object state is able to call execute(). If it is able to,
     * allow execution and toggle canExecute, else throw CommandException.
     * @throws CommandException to prevent execution.
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
