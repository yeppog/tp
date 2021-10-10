package seedu.address.testutil;

import seedu.address.model.TaskList;
import seedu.address.model.task.Task;

public class TaskListBuilder {
    private TaskList taskList;

    public TaskListBuilder() {
        this.taskList = new TaskList();
    }

    /**
     * Adds the given task to the task list we are building.
     */
    public TaskListBuilder withTask(Task task) {
        this.taskList.addTask(task);
        return this;
    }

    public TaskList build() {
        return this.taskList;
    }
}
