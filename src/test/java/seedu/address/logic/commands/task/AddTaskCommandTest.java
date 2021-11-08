package seedu.address.logic.commands.task;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.task.AddTaskCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskList;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    void execute_validTask_taskAdded() {
        Task task = new TaskBuilder().build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(task);
        String expectedMessage = String.format(MESSAGE_SUCCESS, task);
        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());
        expectedModel.addTask(task);

        assertCommandSuccess(addTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validTaskMultipleContactsTags_taskAdded() {
        Task task = new TaskBuilder()
                .withContacts("Alice", "Bob")
                .withTags("Tag1", "Tag2").build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(task);
        String expectedMessage = String.format(MESSAGE_SUCCESS, task);
        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());
        expectedModel.addTask(task);

        assertCommandSuccess(addTaskCommand, model, expectedMessage, expectedModel);
    }
}
