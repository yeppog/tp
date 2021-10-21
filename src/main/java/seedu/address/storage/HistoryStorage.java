package seedu.address.storage;

import java.util.Optional;

public class HistoryStorage {

    private static class CommandHistoryNode {
        private final String command;
        private CommandHistoryNode previous;
        private CommandHistoryNode next;

        public CommandHistoryNode(String command) {
            this.command = command;
            this.previous = null;
            this.next = null;
        }

        public void addNext(CommandHistoryNode nextNode) {
            this.next = nextNode;
            nextNode.previous = this;
        }

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
    private int length;

    private int currentIndex;

    public HistoryStorage() {

    }

    /**
     * Pushes a new command into the stack.
     * @param command The command that was executed and now being stored in the History stack.
     */
    public void pushCommand(String command) {
        CommandHistoryNode newCommand = new CommandHistoryNode(command);
        if (start == null) {
            this.start = newCommand;
            this.end = newCommand;
        } else {

            this.end.addNext(newCommand);
            this.end = newCommand;
        }
        this.current = newCommand;
        this.length += 1;
    }

    public Optional<String> getPreviousCommand(boolean isNext) {
        CommandHistoryNode currentCommand = this.current;
        if (currentCommand == null) {
            if (!isNext) {
                this.current = this.end;
            } else {
                return Optional.empty();
            }
            return Optional.of(Optional.ofNullable(this.current).orElse(new CommandHistoryNode("")).getCommand());
        }
        if (isNext) {
            this.current = current.next;
        } else {
            this.current = current.getPrevious().orElse(this.start);
        }
        return Optional.ofNullable(currentCommand.getCommand());
    }

    public void resetHistoryPosition() {
        this.current = this.end;
    }


}
