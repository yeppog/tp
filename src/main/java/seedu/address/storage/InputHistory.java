package seedu.address.storage;

import java.util.Optional;

/**
 * Input History encapsulates the String commands that were executed by the user. It is a stack implementation
 * that allows the user to obtain the string of the previous command.
 */
public class InputHistory {

    /**
     * Doubly linked list implementation with explicit pointers.
     */
    private static class CommandHistoryNode {
        private final String command;
        private CommandHistoryNode previous;
        private CommandHistoryNode next;

        /**
         * Basic constructor to create a new CommandHistoryNode
         *
         * @param command String of the command.
         */
        public CommandHistoryNode(String command) {
            this.command = command;
            this.previous = null;
            this.next = null;
        }

        /**
         * Adds a node to the right of the current node. Assigns the previous pointer of the new node to this.
         *
         * @param nextNode Node to be added to the next pointer of the current node.
         */
        public void addNext(CommandHistoryNode nextNode) {
            this.next = nextNode;
            nextNode.previous = this;
        }

        /**
         * Obtains the command encapsulated in the CommandHistoryNode
         *
         * @return String of the command.
         */
        public String getCommand() {
            return this.command;
        }

        public Optional<CommandHistoryNode> getNext() {
            return Optional.ofNullable(this.next);
        }

        public Optional<CommandHistoryNode> getPrevious() {
            return Optional.ofNullable(this.previous);
        }
    }

    private CommandHistoryNode start;
    private CommandHistoryNode end;
    private CommandHistoryNode current;
    private String currentString;

    public InputHistory() {
    }

    /**
     * Pushes a new command into the stack. Resets the current pointer to null to indicate it is at the command
     * after the last command of the stack.
     *
     * @param command The command that was executed and now being stored in the History stack.
     */
    public void pushCommand(String command) {
        CommandHistoryNode newCommand = new CommandHistoryNode(command);
        if (start == null) {
            this.start = newCommand;
        } else {
            this.end.addNext(newCommand);
        }
        this.end = newCommand;
        this.current = null;
        this.currentString = null;
    }

    /**
     * Retrieves the history string by iterating through the stack
     *
     * @param isNext The direction in which we are traversing the doubly linked list.
     * @param currentString The last stored unexecuted input from the user.
     * @return Optional of the String to be shown in the text box.
     */
    public Optional<String> getHistoryString(boolean isNext, String currentString) {
        // if on the last item in stack, we store the current input
        if (this.current == null) {
            this.currentString = currentString;
            if (!isNext) {
                this.current = this.end;
            } else {
                return Optional.of(this.currentString);
            }
            return Optional.of(
                    Optional
                            .ofNullable(this.current)
                            .orElse(new CommandHistoryNode(""))
                            .getCommand()
            );
        }
        if (isNext) {
            this.current = current.next;
        } else {
            this.current = current.getPrevious().orElse(this.start);
        }
        return Optional.of(
                Optional
                        .ofNullable(this.current)
                        .orElse(new CommandHistoryNode(this.currentString))
                        .getCommand()
        );
    }

    /**
     * Resets the history position to the last element. Called when the text box is cleared.
     */
    public void resetHistoryPosition() {
        this.currentString = null;
        this.current = null;
    }

}

