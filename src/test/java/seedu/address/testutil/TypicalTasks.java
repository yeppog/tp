package seedu.address.testutil;

import seedu.address.model.TaskList;
import seedu.address.model.task.Task;

import java.util.ArrayList;
import java.util.List;

public class TypicalTasks {
    public static final Task BUY_GROCERIES = new TaskBuilder().build();
    public static final Task DO_HOMEWORK = new TaskBuilder().withTitle("Do homework").withDescription("Math, physics and chemistry").build();

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static TaskList getTypicalTaskList() {
        TaskList tl = new TaskList();
        for (Task task : getTypicalTasks()) {
            tl.addTask(task);
        }
        return tl;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>();
    }
}
