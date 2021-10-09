package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

public class TaskListPanel extends UiPart<Region> {

    @FXML
    private ListView<Task> taskListView;

    /**
     * Creates a TaskListPanel displaying a given list of tasks.
     * @param taskList the list of tasks to display
     */
    public TaskListPanel(ObservableList<Task> taskList) {
        super("TaskListPanel.fxml");
        taskListView.setItems(taskList);
        taskListView.setCellFactory(task -> new TaskListViewCell());
    }
}
