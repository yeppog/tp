package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INTERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MODULE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskList;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;

class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    void execute_allFieldsSpecified_success() {
        Task editedTask = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);
        Task task = model.getTaskList().getTasks().get(INDEX_FIRST_PERSON.getZeroBased());

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());
        expectedModel.setTask(task, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_someFieldsSpecified_success() {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withTitle(VALID_TITLE_REPORT).withDescription(VALID_DESCRIPTION_REPORT)
                .withTags(VALID_TAG_MODULE).build();

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_REPORT)
                .withDescription(VALID_DESCRIPTION_REPORT).withTags(VALID_TAG_MODULE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()) , new UserPrefs());
        expectedModel.setTask(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_REPORT).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    void undo_unfilteredEditedAddressBook_success() {
        Model originalModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());

        Task editedTask = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);
        Task task = model.getTaskList().getTasks().get(INDEX_FIRST_PERSON.getZeroBased());

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());
        expectedModel.setTask(task, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
        model.getCommandHistory().pushCommand(editTaskCommand);

        String successMessage = UndoCommand.MESSAGE_UNDO_SUCCESS + expectedMessage;
        assertCommandSuccess(new UndoCommand(), model, successMessage, originalModel);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_PERSON, DESC_REPORT);

        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor copyDescriptor = new EditTaskCommand.EditTaskDescriptor(DESC_REPORT);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_PERSON, DESC_REPORT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_PERSON, DESC_INTERVIEW)));
    }
}
