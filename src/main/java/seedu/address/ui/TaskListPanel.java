package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.TaskList;
import seedu.address.model.task.Task;

import java.net.URL;

public class TaskListPanel extends UiPart<Region> {

    @FXML
    ListView<Task> taskListView;

    public TaskListPanel(ObservableList<Task> taskList) {
        super("TaskListPanel.fxml");
        taskListView.setItems(taskList);
        taskListView.setCellFactory(task -> new TaskListViewCell());

//        Label label = new Label("Tasks");
//        label.setFont(new Font(24));
//        label.setPadding(new Insets(16, 16, 0, 16));
//        VBox box = new VBox(label, listView);
//        box.getStyleClass().add("pane-with-border");
//        box.setSpacing(8);
    }


}
