package seedu.address.ui;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskCard.fxml";

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label timestamp;

    @FXML
    private Label description;

    @FXML
    private CheckBox isCompleted;

    private final Task task;

    /**
     * Creates a card representing a task. Used in a task list to display a task.
     * @param task The task to represent
     * @param oneIndex The position of the task in the list in one-based indexing
     */
    public TaskCard(Task task, int oneIndex) {
        super(FXML);

        this.task = task;

        id.setText(oneIndex + ". ");
        name.setText(task.getTitle());
        description.setText(task.getDescription());
        timestamp.setText(Optional.ofNullable(task.getTimestamp()).map(Object::toString).orElse(""));
        isCompleted.setText("");
        isCompleted.setSelected(false);
    }
}
