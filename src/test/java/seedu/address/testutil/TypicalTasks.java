package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskList;
import seedu.address.model.task.Task;

public class TypicalTasks {
    public static final Task BUY_GROCERIES = new TaskBuilder().build();
    public static final Task DO_HOMEWORK = new TaskBuilder()
            .withTitle("Do homework")
            .withDescription("Math, physics and chemistry")
            .withTags("important", "homework")
            .build();
    public static final Task CLEAN_ROOM = new TaskBuilder()
            .withTitle("Clean my room")
            .withTags("important")
            .withContacts("Mother")
            .build();
    public static final Task ARRANGE_MEETING = new TaskBuilder()
            .withTitle("Arrange meeting")
            .withTags("work", "important")
            .withDone(true)
            .withContacts(VALID_NAME_AMY)
            .build();

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static TaskList getTypicalTaskList() {
        TaskList tl = new TaskList();
        for (Task task : getTypicalTasks()) {
            tl.addTask(task);
        }
        return tl;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(
                BUY_GROCERIES,
                DO_HOMEWORK,
                CLEAN_ROOM,
                ARRANGE_MEETING
        ));
    }
}
