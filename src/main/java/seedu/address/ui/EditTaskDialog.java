package seedu.address.ui;

import static seedu.address.commons.util.AppUtil.getImage;
import static seedu.address.ui.UiManager.ICON_APPLICATION;

import java.util.HashSet;
import java.util.Optional;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;

public class EditTaskDialog extends UiPart<Region> {
    private static final String FXML = "EditTaskDialog.fxml";

    @FXML
    private TextField title;

    @FXML
    private TextField description;

    @FXML
    private TextField timestamp;

    @FXML
    private CheckBox isDone;

    @FXML
    private TextField tagInput;

    @FXML
    private FlowPane tagPane;

    @FXML
    private Label tagErrorLabel;

    @FXML
    private Label titleErrorLabel;

    private final ObservableSet<Tag> tags;
    private final Dialog<Task> dialog;

    /**
     * Creates a dialog to edit the given task. If the given task is null, creates a new task.
     * @param t The task to edit
     */
    public EditTaskDialog(Task t) {
        super(FXML);

        // Initialize final values
        dialog = new Dialog<>();
        tags = Optional.ofNullable(t)
                .map(Task::getTags)
                .map(tags -> tags.toArray(new Tag[]{}))
                .map(FXCollections::observableSet)
                .orElse(FXCollections.observableSet());

        initializeDialog(t != null);
        initializeFields(t);
    }

    private void initializeFields(Task t) {
        // Set fields to task content
        Optional<Task> task = Optional.ofNullable(t);
        title.setText(task.map(Task::getTitle).orElse(""));
        description.setText(task.flatMap(Task::getDescription).orElse(""));
        timestamp.setText(task.flatMap(Task::getTimestamp).map(Timestamp::toString).orElse(""));
        isDone.setSelected(task.map(Task::isDone).orElse(false));
        tags.stream()
                .map(tag -> new DeletableChip(tag.tagName, () -> tags.remove(tag)).getRoot())
                .forEach(tagPane.getChildren()::add);

        // Initialize error labels
        tagErrorLabel.setText("");
        titleErrorLabel.setText("Title cannot be empty");
        titleErrorLabel.getStyleClass().add("error");
        titleErrorLabel.setVisible(false);
        titleErrorLabel.setManaged(false);

        // Clear empty title error when anything is typed into the title
        title.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            titleErrorLabel.setVisible(false);
            titleErrorLabel.setManaged(false);
            if (event.getCode().equals(KeyCode.ENTER)) {
                event.consume();
                timestamp.requestFocus();
            }
        });

        // Create new tag when "enter" pressed in tag input
        tagInput.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            tagErrorLabel.setText("");
            if (event.getCode().equals(KeyCode.ENTER)) {
                event.consume();
                try {
                    String text = tagInput.getText();
                    tagInput.clear();
                    Tag tag = new Tag(text);
                    tags.add(tag);
                } catch (IllegalArgumentException e) {
                    tagErrorLabel.setText(e.getMessage());
                }
            }
        });

        // When tag list changes, update tag list
        tags.addListener((Observable observable) -> {
            tagPane.getChildren().clear();
            tags.stream()
                    .map(tag -> new DeletableChip(tag.tagName, () -> tags.remove(tag)).getRoot())
                    .forEach(tagPane.getChildren()::add);
        });
    }

    private void initializeDialog(boolean isEditingTask) {
        //Set the application icon.
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getImage(ICON_APPLICATION));

        // Set dialog title
        dialog.setTitle(isEditingTask ? "Edit Task" : "Add Task");

        // Set dialog size
        dialog.getDialogPane().setPrefWidth(600);
        dialog.setResizable(true);

        // Set dialog content
        dialog.getDialogPane().setContent(getRoot());
        dialog.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        dialog.getDialogPane().getStyleClass().add("stack-pane");

        // Add dialog buttons
        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);

        // Disallow save if form content is invalid
        dialog.getDialogPane().lookupButton(buttonTypeOk).addEventFilter(ActionEvent.ACTION, event -> {
            if (!areFieldsValid()) {
                event.consume();
            }
        });

        // Return new Task when "OK" is pressed
        dialog.setResultConverter((buttonType) -> {
            if (buttonType.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                return getTask();
            } else {
                return null;
            }
        });
    }

    private boolean areFieldsValid() {
        if (title.getText().isBlank()) {
            titleErrorLabel.setVisible(true);
            titleErrorLabel.setManaged(true);
            return false;
        } else {
            titleErrorLabel.setVisible(false);
            titleErrorLabel.setManaged(false);
            return true;
        }
    }

    public Dialog<Task> getDialog() {
        return dialog;
    }

    private Task getTask() {
        return new Task(
                title.getText(),
                Optional.of(description.getText()).filter(text -> !text.isBlank()).orElse(null),
                Optional.of(timestamp.getText()).filter(text -> !text.isBlank()).map(Timestamp::new).orElse(null),
                tags,
                isDone.isSelected(),
                new HashSet<>()
        );
    }
}
