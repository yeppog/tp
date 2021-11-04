package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void undo_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        Model originalModel = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        UndoableCommand clearCommand = new ClearCommand();
        String expectedMessage = ClearCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(clearCommand, model, expectedMessage, expectedModel);
        model.getCommandHistory().pushCommand(clearCommand);

        String successUndoMessage = UndoCommand.MESSAGE_UNDO_SUCCESS + expectedMessage;
        assertCommandSuccess(new UndoCommand(), model, successUndoMessage, originalModel);

        String successRedoMessage = RedoCommand.MESSAGE_REDO_SUCCESS + expectedMessage;
        assertCommandSuccess(new RedoCommand(), model, successRedoMessage, expectedModel);
    }

}
