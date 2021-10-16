package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskList;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

class DoneTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    void execute_validIndex_success() {
        Task editedTask = new TaskBuilder().withDone(true).build();
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DoneTaskCommand.MESSAGE_SUCCESS, editedTask);
        Task task = model.getTaskList().getTasks().get(INDEX_FIRST_PERSON.getZeroBased());

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());
        expectedModel.setTask(task, editedTask);

        assertCommandSuccess(doneTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_invalidTaskIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(outOfBoundIndex);

        assertCommandFailure(doneTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    void execute_taskAlreadyCompleted_failure() {
        Task editedTask = new TaskBuilder().withDone(true).build();
        Task task = model.getTaskList().getTasks().get(INDEX_FIRST_PERSON.getZeroBased());

        Model modelAlreadyCompleted = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());
        modelAlreadyCompleted.setTask(task, editedTask);

        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(doneTaskCommand, modelAlreadyCompleted,
                String.format(DoneTaskCommand.MESSAGE_ALREADY_DONE, editedTask));
    }

    @Test
    void equals() {
        DoneTaskCommand doneFirstCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);
        DoneTaskCommand doneSecondCommand = new DoneTaskCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(doneFirstCommand.equals(doneFirstCommand));

        // same values -> returns true
        DoneTaskCommand doneFirstCommandCopy = new DoneTaskCommand(INDEX_FIRST_PERSON);
        assertTrue(doneFirstCommand.equals(doneFirstCommandCopy));

        // different types -> returns false
        assertFalse(doneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doneFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(doneFirstCommand.equals(doneSecondCommand));
    }
}
