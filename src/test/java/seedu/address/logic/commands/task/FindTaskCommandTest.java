package seedu.address.logic.commands.task;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

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



    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TaskContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TaskContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
