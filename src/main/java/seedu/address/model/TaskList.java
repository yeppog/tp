package seedu.address.model;

import java.util.List;

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

    /**
     * Removes the last item in the list. Bypass for undoing add tasks.
     */
    public void removeTaskAtLastIndex() {
        if (tasks.size() > 0) {
            tasks.remove(tasks.size() - 1);
        }
    }

    public void setTask(Task oldTask, Task newTask) {
        tasks.set(tasks.indexOf(oldTask), newTask);
    }

    public int indexOf(Task task) {
        return tasks.indexOf(task);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && TaskList.taskListEquals(tasks, ((TaskList) other).tasks));
    }

    private static boolean taskListEquals(List<? extends Task> taskList1, List<? extends Task> taskList2) {
        if (taskList1.size() != taskList2.size()) {
            return false;
        }

        for (int i = 0; i < taskList1.size(); i++) {
            if (!taskList1.get(i).deepEquals(taskList2.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}
