package seedu.address.ui;

import java.util.Optional;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.Command;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilter;

public class TaskListPanel extends UiPart<Region> {

    @FXML
    private ComboBox<TaskFilter> filterComboBox;

    @FXML
    private ListView<Task> taskListView;

    @FXML
    private FlowPane filterFlowPane;

    @FXML
    private Button newTaskButton;

    private final ObservableList<TaskFilter> availableTaskFilters;
    private final ObservableList<TaskFilter> selectedTaskFilters;

    /**
     * Creates a TaskListPanel displaying a given list of tasks.
     * @param taskList the list of tasks to display
     */
    public TaskListPanel(
            ObservableList<Task> taskList,
            ObservableList<TaskFilter> selectableTaskFilters,
            ObservableList<TaskFilter> selectedTaskFilters,
            Consumer<TaskFilter> addTaskFilter,
            Consumer<TaskFilter> removeTaskFilter,
            Consumer<Task> addTask,
            Consumer<? super Command> commandExecutor) {
        super("TaskListPanel.fxml");
        this.availableTaskFilters = selectableTaskFilters;
        this.selectedTaskFilters = selectedTaskFilters;

        // Initialize task list
        taskListView.setItems(taskList);
        taskListView.setCellFactory(tasks -> new TaskListViewCell(commandExecutor));

        // Initialize list of selectable filters
        filterComboBox.setCellFactory(taskFilters -> new TaskFilterCell());
        filterComboBox.setPromptText(null);
        Label placeholder = new Label("No more filters");
        placeholder.getStyleClass().add("italics");
        filterComboBox.setPlaceholder(placeholder);
        filterComboBox.setItems(selectableTaskFilters);
        filterComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // When a filter is selected, reset the combo box's current selection
                Platform.runLater(() -> {
                    filterComboBox.getSelectionModel().clearSelection();
                    filterComboBox.hide();
                    addTaskFilter.accept(newValue);
                });
            }
        });

        // Update FlowPane containing a node for each selected filter
        selectedTaskFilters.addListener((Observable observable) -> {
            filterFlowPane.getChildren().clear();
            selectedTaskFilters.stream()
                    .map(taskFilter -> new DeletableChip(taskFilter.toDisplayString(), () ->
                            removeTaskFilter.accept(taskFilter)).getRoot())
                    .forEach(filterFlowPane.getChildren()::add);
        });

        newTaskButton.setOnAction(event -> {
            EditTaskDialog editTaskDialog = new EditTaskDialog(null);
            Optional<Task> newTask = editTaskDialog.getDialog().showAndWait();
            newTask.ifPresent(addTask);
        });
    }
}
