package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class UndoCommandTest {

    private final Model actualModel = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    void execute_invalid_undo() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandTestUtil.assertCommandSuccess(undoCommand, model,
                "Cannot undo any further.", this.actualModel);
    }
}
