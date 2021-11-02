package seedu.address.logic.commands.task;

import static seedu.address.logic.commands.task.EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/*
 * Sets a given task in the task list with another given task.
 */
public class SetTaskCommand extends UndoableCommand {
    private Task oldTask;
    private Task newTask;

    /**
     * Creates a SetTaskCommand with the given oldTask and newTask.
     * @param oldTask The task to replace
     * @param newTask The new task to replace the old task with
     */
    public SetTaskCommand(Task oldTask, Task newTask) {
        this.oldTask = oldTask;
        this.newTask = newTask;
    }

    @Override
    protected CommandResult executeDo(Model model) {
        newTask = model.setTask(oldTask, newTask);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS,
                newTask));
    }

    @Override
    protected CommandResult executeUndo(Model model) {
        oldTask = model.setTask(newTask, oldTask);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS,
                newTask));
    }
}
