package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

/**
 * A read-only instance of a task list.
 */
public interface ReadOnlyTaskList {
    ObservableList<Task> getTasks();
}
