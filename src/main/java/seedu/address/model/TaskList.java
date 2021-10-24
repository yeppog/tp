package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

public class TaskList implements ReadOnlyTaskList {
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    public TaskList() {

    }

    public TaskList(ReadOnlyTaskList toBeCopied) {
        tasks.addAll(toBeCopied.getTasks());
    }

    @Override
    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addTaskAtIndex(Task task, int index) {
        tasks.add(index, task);
    }

    public void removeTask(Task deletedTask) {
        tasks.remove(deletedTask);
    }

    public void setTask(Task oldTask, Task newTask) {
        tasks.set(tasks.indexOf(oldTask), newTask);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && tasks.equals(((TaskList) other).tasks));
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}
