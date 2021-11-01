package seedu.address.logic.commands.task;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

import static seedu.address.logic.commands.task.EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS;

public class SetTaskCommand extends UndoableCommand {
    private final Task oldTask;
    private final Task newTask;

    public SetTaskCommand(Task oldTask, Task newTask) {
        this.oldTask = oldTask;
        this.newTask = newTask;
    }

    @Override
    protected CommandResult executeDo(Model model) {
        model.setTask(oldTask, newTask);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS,
                newTask));
    }

    @Override
    protected CommandResult executeUndo(Model model) {
        model.setTask(newTask, oldTask);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS,
                newTask));
    }
}
