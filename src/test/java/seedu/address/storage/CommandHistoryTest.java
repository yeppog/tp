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
        AddTaskCommand command1 = new AddTaskCommand(new TaskBuilder().build());
        AddTaskCommand command2 = new AddTaskCommand(new TaskBuilder().build());
        AddTaskCommand command3 = new AddTaskCommand(new TaskBuilder().build());
        history.pushCommand(command1);
        history.pushCommand(command2);
        history.pushCommand(command3);
        assertDoesNotThrow(() -> command1.execute(model));
        assertDoesNotThrow(() -> command2.execute(model));
        assertDoesNotThrow(() -> command3.execute(model));
        // navigate backwards
        history.undo().ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        history.undo().ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        history.undo().ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        history.undo().ifPresent(x -> assertThrows(CommandException.class, () -> x.undo(model)));
    }

    @Test
    public void redo_overflow_returnsCorrectResult() {
        ModelManager model = new ModelManager();
        CommandHistory history = new CommandHistory(15);
        AddTaskCommand command1 = new AddTaskCommand(new TaskBuilder().build());
        AddTaskCommand command2 = new AddTaskCommand(new TaskBuilder().build());
        AddTaskCommand command3 = new AddTaskCommand(new TaskBuilder().build());
        history.pushCommand(command1);
        history.pushCommand(command2);
        history.pushCommand(command3);
        assertDoesNotThrow(() -> command1.execute(model));
        assertDoesNotThrow(() -> command2.execute(model));
        assertDoesNotThrow(() -> command3.execute(model));
        // navigate backwards
        history.undo()
                .ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        history.undo()
                .ifPresent(x -> assertDoesNotThrow(() -> x.undo(model)));
        // go forward twice
        history.redo()
                .ifPresent(x -> assertDoesNotThrow(() -> x.execute(model)));
        history.redo()
                .ifPresent(x -> assertDoesNotThrow(() -> x.execute(model)));
        // ensure cannot over redo
        history.redo()
                .ifPresent(x -> assertThrows(CommandException.class, () -> x.undo(model)));
    }

    @Test
    public void empty_stack_returnsCorrectResult() {
        ModelManager model = new ModelManager();
        CommandHistory history = new CommandHistory(15);
        history.redo()
                .ifPresent(x -> assertThrows(CommandException.class, () -> x.undo(model)));
        history.undo()
                .ifPresent(x -> assertThrows(CommandException.class, () -> x.undo(model)));
    }

}
