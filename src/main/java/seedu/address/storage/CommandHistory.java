package seedu.address.storage;

import java.util.Optional;

import seedu.address.logic.commands.UndoableCommand;

/**
 * UserUndoStorage contains a stack of undo instructions to be executed when the user wants to
 * perform an undo action.
 */
public class CommandHistory {

    private int stackSize;
    private final int maxStackSize;
    private CommandHistoryNode start;
    private CommandHistoryNode end;
    private CommandHistoryNode current;

    private static class CommandHistoryNode {
        private final UndoableCommand command;
        private CommandHistoryNode previous;
        private CommandHistoryNode next;

        /**
         * Simple constructor to initialise a CommandHistoryNode
         *
         * @param command The command to be encapsulated.
         */
        public CommandHistoryNode(UndoableCommand command) {
            this.command = command;
            this.previous = null;
            this.next = null;
        }

        /**
         * Links the pointers of the current node and the nextNode.
         *
         * @param nextNode node to be added
         */
        public void addNext(CommandHistoryNode nextNode) {
            this.next = nextNode;
            nextNode.previous = this;
        }

        /**
         * Replaces the current node with another node. If the current node does not
         * have a previous node, this operation does nothing and returns False.
         *
         * @param nodeToReplaceWith The node to replace the current node with
         * @return a boolean to signify if the replace was done successfully.
         */
        public boolean replace(CommandHistoryNode nodeToReplaceWith) {
            if (previous != null) {
                previous.next = nodeToReplaceWith;
                nodeToReplaceWith.previous = previous;
                return true;
            }
            return false;
        }

        public UndoableCommand getCommand() {
            return this.command;
        }

        public Optional<CommandHistoryNode> getNext() {
            return Optional.ofNullable(this.next);
        }

        public Optional<CommandHistoryNode> getPrevious() {
            return Optional.ofNullable(this.previous);
        }

    }

    /**
     * Constructor to initialise the CommandHistory object
     * @param maxStackSize The maximum number of undos a user can do.
     *                     Helps to prevent any potential memory overflow if too many commands are executed.
     */
    public CommandHistory(int maxStackSize) {
        this.stackSize = 0;
        this.maxStackSize = maxStackSize;
    }

    /**
     * Adds a command into to the command history stack. Delete from the start if the stackSize
     * grows beyond the maximum stack size.
     *
     * @param command Command to be inserted to the history stack
     */
    public void pushCommand(UndoableCommand command) {
        CommandHistoryNode newCommand = new CommandHistoryNode(command);
        if (this.isStackFull()) {
            this.start = this.start.next;
            this.stackSize -= 1;
        }
        if (this.start == null) {
            this.start = newCommand;
        } else {
            if (this.current != null) {
                if (!this.current.replace(newCommand)) {
                    this.start = newCommand;
                }
            } else {
                this.end.addNext(newCommand);
            }
        }
        this.end = newCommand;
        this.stackSize += 1;
        this.current = null;
    }

    /**
     * Checks if the stack is full. For internal class use only.
     *
     * @return if the stack is full.
     */
    private boolean isStackFull() {
        return this.stackSize >= this.maxStackSize;
    }


    public Optional<UndoableCommand> undo() {
        return getHistoryCommand(false);
    }

    public Optional<UndoableCommand> redo() {
        return getHistoryCommand(true);
    }

    /**
     * Retrieves the requested command from the stack. Returns the next item in the stack if
     * isNext is true, and returns the current item in the stack is isNext is false.
     *
     * @return Optional of the Command. If the stack is empty, returns an empty Optional.
     */

    private Optional<UndoableCommand> getHistoryCommand(boolean isNext) {
        CommandHistoryNode toReturn = this.current;
        if (this.current == null) {
            if (isNext) {
                return Optional.empty();
            } else {
                this.current = this.end;
                return Optional.ofNullable(this.end).map(CommandHistoryNode::getCommand);
            }
        }
        if (isNext) {
            this.current = current.next;
        } else {
            if (this.current.previous == null) {
                return Optional.empty();
            }
            this.current = this.current.getPrevious().orElse(this.start);
            toReturn = this.current;
        }
        return Optional.ofNullable(toReturn).map(CommandHistoryNode::getCommand);
    }

}
