package seedu.address.storage;

import java.util.LinkedList;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.UndoCommand;

/**
 * UserUndoStorage contains a stack of undo instructions to be executed when the user wants to
 * perform an undo action.
 */
public class CommandHistory {

    private int stackSize;
    private int maxStackSize;
    private LinkedList<Command> commandStack;

    /**
     * Constructor to initialise the CommandHistory object
     * @param maxStackSize The maximum number of undo a user can make at any time.
     *                     Helps to prevent any potential memory overflow if too many commands are executed.
     */
    public CommandHistory(int maxStackSize) {
        this.stackSize = 0;
        this.maxStackSize = maxStackSize;
        this.commandStack = new LinkedList<>();
    }

    /**
     * Adds a command to the command history stack.
     * @param command Command to be inserted to the history stack
     */
    public void pushCommand(Command command) {
        if (command instanceof UndoCommand) {
            return;
        }
        if (this.isStackFull()) {
            commandStack.removeFirst();
        }
        commandStack.addLast(command);
    }

    /**
     * Checks if the stack is full. For internal class use only.
     * @return boolean if the stack is full.
     */
    private boolean isStackFull() {
        return this.stackSize == this.maxStackSize;
    }

    /**
     * Pops the last command from the stack and removes it.
     * @return Optional of the Command. If the stack is empty, returns an empty Optional.
     */
    public Optional<Command> popLastCommand() {
        if (commandStack.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.commandStack.removeLast());
        }
    }

    @Override
    public String toString() {
        return this.commandStack.toString();
    }
}
