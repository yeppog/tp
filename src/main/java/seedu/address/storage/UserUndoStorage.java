package seedu.address.storage;

import java.util.LinkedList;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.UndoCommand;

/**
 * UserUndoStorage contains a stack of undo instructions to be executed when the user wants to
 * perform an undo action.
 */
public class UserUndoStorage {

    private int stackSize;
    private int maxStackSize;
    private LinkedList<Command> commandStack;


    public UserUndoStorage(int maxStackSize) {
        this.stackSize = 0;
        this.maxStackSize = maxStackSize;
        this.commandStack = new LinkedList<>();
    }

    public void addUndoCommand(Command command) {
        if (command instanceof UndoCommand) {
            return;
        }
        if (this.isStackFull()) {
            commandStack.removeFirst();
            commandStack.addLast(command);
        } else {
            commandStack.addLast(command);
        }
    }

    private boolean isStackFull() {
        return this.stackSize == this.maxStackSize;
    }

    public Command getMostRecentUndoCommand() {
        return this.commandStack.pollLast();
    }

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
