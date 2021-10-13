package seedu.address.logic.commands.task;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.model.TaskList;
import seedu.address.testutil.TaskBuilder;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

class DoneTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    void execute_validIndex_success() {
        Task editedTask = new TaskBuilder().withDone(true).build();
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DoneTaskCommand.MESSAGE_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());
        expectedModel.setTask(INDEX_FIRST_PERSON.getZeroBased(), editedTask);

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

        Model modelAlreadyCompleted = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());
        modelAlreadyCompleted.setTask(INDEX_FIRST_PERSON.getZeroBased(), editedTask);

        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(doneTaskCommand, modelAlreadyCompleted,
                String.format(DoneTaskCommand.MESSAGE_ALREADY_DONE, editedTask));
    }
}