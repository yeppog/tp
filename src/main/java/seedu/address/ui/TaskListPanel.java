package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;
import seedu.address.ui.exceptions.GuiException;

public class TaskListPanel extends UiPart<Region> {

    @FXML
    private ListView<Task> taskListView;

    /**
     * Creates a TaskListPanel displaying a given list of tasks.
     * @param taskList the list of tasks to display
     */
    public TaskListPanel(ObservableList<Task> taskList, TaskEditor taskEditor) {
        super("TaskListPanel.fxml");
        taskListView.setItems(taskList);
        taskListView.setCellFactory(task -> new TaskListViewCell(taskEditor));
    }

    @FunctionalInterface
    interface TaskEditor {
        void updateTask(Task oldTask, Task newTask) throws GuiException;
    }
}
