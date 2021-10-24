package seedu.address.ui;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.filters.TaskFilters.TaskFilter;

public class TaskFilterChip extends UiPart<Region> {
    @FXML
    private Label filterNameLabel;

    @FXML
    private Button removeButton;

    /**
     * Creates a chip UI element representing a selected task filter
     * @param taskFilter The selected task filter
     * @param removeTaskFilter The function to call to remove a task filter
     */
    public TaskFilterChip(TaskFilter taskFilter, Consumer<TaskFilter> removeTaskFilter) {
        super("TaskFilterChip.fxml");
        filterNameLabel.setText(taskFilter.toString());
        removeButton.setOnAction((actionEvent) -> removeTaskFilter.accept(taskFilter));
    }
}
