package seedu.address.logic.commands.task;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.TaskContainsKeywordsPredicate;


public class FindTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    void execute_singleKeyword_singleTaskFound() {
        String expectedMessage = String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskContainsKeywordsPredicate testPredicate = preparePredicate("homework");
        FindTaskCommand testCommand = new FindTaskCommand(testPredicate);
        expectedModel.updateFilteredTaskList(testPredicate);
        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_singleKeywordWithExistingFilter_filterReplaced() {
        String expectedMessage = String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskContainsKeywordsPredicate testPredicate = preparePredicate("homework");
        FindTaskCommand testCommand = new FindTaskCommand(testPredicate);
        expectedModel.updateFilteredTaskList(testPredicate);

        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);

        TaskContainsKeywordsPredicate nextTestPredicate = preparePredicate("room");
        FindTaskCommand nextTestCommand = new FindTaskCommand(nextTestPredicate);
        expectedModel.updateFilteredTaskList(testPredicate);

        assertCommandSuccess(nextTestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_zeroKeywords_allTasksFound() {
        String expectedMessage = String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, 4);
        TaskContainsKeywordsPredicate testPredicate = preparePredicate("");
        FindTaskCommand testCommand = new FindTaskCommand(testPredicate);
        expectedModel.updateFilteredTaskList(testPredicate);
        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void undo_singleKeywordWithExistingFilter_success() {
        Model originalModel = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

        String expectedMessage = String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskContainsKeywordsPredicate testPredicate = preparePredicate("homework");
        FindTaskCommand findTaskCommand = new FindTaskCommand(testPredicate);
        expectedModel.updateFilteredTaskList(testPredicate);

        assertCommandSuccess(findTaskCommand, model, expectedMessage, expectedModel);
        model.getCommandHistory().pushCommand(findTaskCommand);

        String successMessage = UndoCommand.MESSAGE_UNDO_SUCCESS + expectedMessage;
        assertCommandSuccess(new UndoCommand(), model, successMessage, originalModel);

    }



    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TaskContainsKeywordsPredicate preparePredicate(String userInput) {
        if (userInput.isEmpty()) {
            return TaskContainsKeywordsPredicate.SHOW_ALL_TASKS_PREDICATE;
        } else {
            return new TaskContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
        }
    }

}
