package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    protected boolean canExecute;

    /**
     * Default constructor for all classes that implement Command.
     * @param canExecute Boolean for if the class is able to run the execute method.
     */
    public Command(boolean canExecute) {
        this.canExecute = canExecute;
    }

    /**
     * Constructor that initialises canExecute to be true when super is not called in child class.
     */
    public Command() {
        this.canExecute = true;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Checks if the current object state is able to call execute(). If it is able to,
     * allow execution and toggle canExecute, else throw CommandException.
     * @throws CommandException to prevent execution.
     */
    public void canExecute() throws CommandException {
        if (!this.canExecute) {
            throw new CommandException(Messages.MESSAGE_UNABLE_TO_EXECUTE);
        }
        this.canExecute = false;
    }

    /**
     * Checks if the current object state is able to call undo(). If it is able to,
     * allow undo and toggle canExecute, else throw CommandException.
     * @throws CommandException to prevent undo.
     */
    public void canUndo() throws CommandException {
        if (this.canExecute) {
            throw new CommandException(Messages.MESSAGE_UNABLE_TO_UNDO);
        }
        this.canExecute = true;
    }

    public CommandResult undo(Model model) throws CommandException {
        return new CommandResult("Undo not supported for this command, or cannot undo any further");
    }

}
