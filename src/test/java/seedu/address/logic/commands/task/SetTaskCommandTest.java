package seedu.address.logic.commands.task;

import org.junit.jupiter.api.Test;
import seedu.address.model.*;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

class SetTaskCommandTest {
    @Test
    void execute_setTaskAsNewTask_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        Task newTask = new TaskBuilder().build();

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, newTask);
        Task task = model.getTaskList().getTasks().get(INDEX_FIRST_PERSON.getZeroBased());

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()), new UserPrefs());
        expectedModel.setTask(task, newTask);

        assertCommandSuccess(new SetTaskCommand(task, newTask), model, expectedMessage, expectedModel);
    }

}