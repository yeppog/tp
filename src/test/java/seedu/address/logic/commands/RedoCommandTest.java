package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    void execute_invalid_undo() {
        RedoCommand redoCommand = new RedoCommand();
        CommandTestUtil.assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_NOT_UNDONE, model);
    }

}
