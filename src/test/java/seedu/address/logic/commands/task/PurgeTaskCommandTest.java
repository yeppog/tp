package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskList;
import seedu.address.model.UserPrefs;

class PurgeTaskCommandTest {

    @Test
    void execute_emptyAddressBook_failure() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandFailure(new PurgeTaskCommand(), model, PurgeTaskCommand.MESSAGE_NO_TASK_TO_PURGE);
    }

    @Test
    void execute_unfilteredAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new TaskList(), new UserPrefs());

        assertCommandSuccess(new PurgeTaskCommand(), model, PurgeTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void undo_unfilteredPurgedAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new TaskList(), new UserPrefs());

        UndoableCommand purgeTask = new PurgeTaskCommand();
        assertCommandSuccess(purgeTask, model, PurgeTaskCommand.MESSAGE_SUCCESS, expectedModel);
        model.getCommandHistory().pushCommand(purgeTask);

        Model originalModel = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        String successMessage = UndoCommand.MESSAGE_UNDO_SUCCESS + PurgeTaskCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(new UndoCommand(), model, successMessage, originalModel);
    }

    @Test
    public void equals() {
        final PurgeTaskCommand standardCommand = new PurgeTaskCommand();

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type -> returns true
        assertTrue(standardCommand.equals(new PurgeTaskCommand()));
    }

}
