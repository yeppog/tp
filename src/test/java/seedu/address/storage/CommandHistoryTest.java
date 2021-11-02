package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TaskBuilder;

class CommandHistoryTest {


    @Test
    public void undo_overflow_returnsCorrectResult() {
        ModelManager model = new ModelManager();
        CommandHistory history = new CommandHistory(15);
        AddTaskCommand command = new AddTaskCommand(new TaskBuilder().build());
        history.pushCommand(command);
        history.pushCommand(command);
        history.pushCommand(command);
        // navigate backwards
        history.getHistoryCommand(false).ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        history.getHistoryCommand(false).ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        history.getHistoryCommand(false).ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        history.getHistoryCommand(false).ifPresent(x -> assertThrows(CommandException.class, () -> x.undo(model)));
    }

    @Test
    public void redo_overflow_returnsCorrectResult() {
        ModelManager model = new ModelManager();
        CommandHistory history = new CommandHistory(15);
        AddTaskCommand command = new AddTaskCommand(new TaskBuilder().build());
        history.pushCommand(command);
        history.pushCommand(command);
        history.pushCommand(command);
        // navigate backwards
        history.getHistoryCommand(false)
                .ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        history.getHistoryCommand(false)
                .ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        // go forward twice
        history.getHistoryCommand(true)
                .ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        history.getHistoryCommand(true)
                .ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        // ensure cannot over redo
        history.getHistoryCommand(true)
                .ifPresent(x -> assertThrows(CommandException.class, () -> x.undo(model)));
    }

    @Test
    public void empty_stack_returnsCorrectResult() {
        ModelManager model = new ModelManager();
        CommandHistory history = new CommandHistory(15);
        AddTaskCommand command = new AddTaskCommand(new TaskBuilder().build());
        history.getHistoryCommand(true)
                .ifPresent(x -> assertThrows(CommandException.class, () -> x.undo(model)));
        history.getHistoryCommand(false)
                .ifPresent(x -> assertThrows(CommandException.class, () -> x.undo(model)));
    }

}
