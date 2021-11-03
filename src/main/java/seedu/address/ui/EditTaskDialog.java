package seedu.address.ui;

import static seedu.address.commons.util.AppUtil.getImage;
import static seedu.address.ui.UiManager.ICON_APPLICATION;

import java.time.LocalDate;
import java.util.Optional;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Contact;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;

public class EditTaskDialog extends UiPart<Region> {
    private static final String FXML = "EditTaskDialog.fxml";
    private static final String PROMPT_ENTER_TAG = "Press \"enter\" to add tag";
    private static final String PROMPT_ENTER_CONTACT = "Press \"enter\" to add contact";

    @FXML
    private TextField title;

    @FXML
    private Label titleErrorLabel;

    @FXML
    private TextField description;

    @FXML
    private DatePicker timestamp;

    @FXML
    private Label timestampClearLabel;

    @FXML
    private CheckBox isDone;

    @FXML
    private TextField tagInput;

    @FXML
    private FlowPane tagPane;

    @FXML
    private Label tagInfoLabel;

    @FXML
    private TextField contactInput;

    @FXML
    private FlowPane contactPane;

    @FXML
    private Label contactInfoLabel;

    @FXML
    private Label dialogTitleLabel;

    private final ObservableSet<Tag> tags;
    private final ObservableSet<Contact> contacts;
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
        contacts = Optional.ofNullable(t)
                .map(Task::getContacts)
                .map(contacts -> contacts.toArray(new Contact[]{}))
                .map(FXCollections::observableSet)
                .orElse(FXCollections.observableSet());

        initializeDialog(t != null);
        initializeFields(t);
        initializeDatePickerRedirection();
    }

    private void initializeDatePickerRedirection() {
        title.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (new KeyCodeCombination(KeyCode.TAB).match(event)) {
                timestamp.show();
                timestamp.requestFocus();
                event.consume();
            }
        });
        description.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (new KeyCodeCombination(KeyCode.TAB, KeyCombination.SHIFT_DOWN).match(event)) {
                timestamp.show();
                timestamp.requestFocus();
                event.consume();
            }
        });

        new DatePickerSkin(timestamp).getPopupContent().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (new KeyCodeCombination(KeyCode.TAB).match(event)) {
                timestamp.hide();
                description.requestFocus();
                event.consume();
            } else if (new KeyCodeCombination(KeyCode.TAB, KeyCombination.SHIFT_DOWN).match(event)) {
                timestamp.hide();
                title.requestFocus();
                event.consume();
            }
        });
    }

    private void initializeFields(Task t) {
        // Set fields to task content
        Optional<Task> task = Optional.ofNullable(t);
        title.setText(task.map(Task::getTitle).orElse(""));
        description.setText(task.flatMap(Task::getDescription).orElse(""));
        timestamp.setValue(task.flatMap(Task::getTimestamp).map(Timestamp::getDate).orElse(null));
        isDone.setSelected(task.map(Task::isDone).orElse(false));
        tags.stream()
                .map(tag -> new DeletableChip(tag.tagName, () -> tags.remove(tag)).getRoot())
                .forEach(tagPane.getChildren()::add);
        contacts.stream()
                .map(contact -> new DeletableChip(contact.getName().fullName, () -> contacts.remove(contact)).getRoot())
                .forEach(contactPane.getChildren()::add);

        // Disallow timestamp manual input
        timestamp.getEditor().setDisable(true);

        // Initialize error labels
        tagInfoLabel.setText(PROMPT_ENTER_TAG);
        tagInfoLabel.setWrapText(true);
        contactInfoLabel.setText(PROMPT_ENTER_CONTACT);
        contactInfoLabel.setWrapText(true);
        titleErrorLabel.setText("Title cannot be empty");
        titleErrorLabel.getStyleClass().add("error");
        titleErrorLabel.setVisible(false);

        // Show info labels only if focused
        tagInfoLabel.visibleProperty().bind(tagInput.focusedProperty());
        contactInfoLabel.visibleProperty().bind(contactInput.focusedProperty());
        tagInfoLabel.managedProperty().bind(tagInput.focusedProperty());
        contactInfoLabel.managedProperty().bind(contactInput.focusedProperty());

        // Clear empty title error when anything is typed into the title
        title.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            titleErrorLabel.setVisible(false);

            if (new KeyCodeCombination(KeyCode.ENTER).match(event)) {
                event.consume();
            }
        });

        description.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (new KeyCodeCombination(KeyCode.ENTER).match(event)) {
                event.consume();
            }
        });

        // Create new tag when "enter" pressed in tag input
        tagInput.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            tagInfoLabel.prefWidthProperty().bind(getRoot().widthProperty().subtract(32));
            tagInfoLabel.getStyleClass().remove("error");
            tagInfoLabel.setText(PROMPT_ENTER_TAG);
            if (event.getCode().equals(KeyCode.ENTER)) {
                event.consume();
                try {
                    String text = tagInput.getText();
                    Tag tag = new Tag(text);
                    if (tags.contains(tag)) {
                        throw new IllegalArgumentException("Tag already added");
                    }
                    tags.add(tag);
                    tagInput.clear();
                } catch (IllegalArgumentException e) {
                    tagInfoLabel.setText(e.getMessage());
                    tagInfoLabel.getStyleClass().add("error");
                }
            }
        });

        // Create new tag when "enter" pressed in tag input
        contactInput.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            contactInfoLabel.prefWidthProperty().bind(getRoot().widthProperty().subtract(32));
            contactInfoLabel.getStyleClass().remove("error");
            contactInfoLabel.setText(PROMPT_ENTER_CONTACT);
            if (event.getCode().equals(KeyCode.ENTER)) {
                event.consume();
                try {
                    String text = contactInput.getText();
                    Contact contact = new Contact(new Name(text));
                    if (contacts.contains(contact)) {
                        throw new IllegalArgumentException("Contact already added");
                    }
                    contacts.add(contact);
                    contactInput.clear();
                } catch (IllegalArgumentException e) {
                    contactInfoLabel.setText(e.getMessage());
                    contactInfoLabel.getStyleClass().add("error");
                }
            }
        });

        tagInput.focusedProperty().addListener((observable) -> {
            if (!contactInput.isFocused()) {
                dialog.getDialogPane().getScene().getWindow().sizeToScene();
            }
        });

        contactInput.focusedProperty().addListener((observable) -> {
            if (!tagInput.isFocused()) {
                dialog.getDialogPane().getScene().getWindow().sizeToScene();
            }
        });

        // When tag list changes, update tag chip list
        tags.addListener((Observable observable) -> {
            tagPane.getChildren().clear();
            tags.stream()
                    .map(tag -> new DeletableChip(tag.tagName, () -> tags.remove(tag)).getRoot())
                    .forEach(tagPane.getChildren()::add);
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        });

        // When contact list changes, update contact chip list
        contacts.addListener((Observable observable) -> {
            contactPane.getChildren().clear();
            contacts.stream()
                    .map(contact ->
                            new DeletableChip(contact.getName().fullName, () -> contacts.remove(contact)).getRoot())
                    .forEach(contactPane.getChildren()::add);
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        });

        // Set timestamp format
        timestamp.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return date.toString();
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(string);
            }
        });

        timestampClearLabel.visibleProperty().bind(timestamp.valueProperty().isNotNull());
        timestampClearLabel.setOnMouseClicked(event -> {
            timestamp.setValue(null);
        });
    }

    private void initializeDialog(boolean isEditingTask) {
        //Set the application icon.
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getImage(ICON_APPLICATION));

        // Set dialog title
        String dialogTitle = isEditingTask ? "Edit Task" : "Add Task";
        dialog.setTitle(dialogTitle);
        dialogTitleLabel.setText(dialogTitle);

        // Set dialog size
        dialog.getDialogPane().setPrefWidth(600);

        // Set dialog content
        dialog.getDialogPane().setContent(getRoot());
        dialog.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        dialog.getDialogPane().getStyleClass().add("background");

        // Add dialog buttons
        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);

        // Disallow save if form content is invalid
        dialog.getDialogPane().lookupButton(buttonTypeOk).addEventFilter(ActionEvent.ACTION, event -> {
            if (!validateFields()) {
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

    private boolean validateFields() {
        boolean isTitleValid = !title.getText().isBlank();
        titleErrorLabel.setVisible(!isTitleValid);
        return isTitleValid;
    }

    public Dialog<Task> getDialog() {
        return dialog;
    }

    private Task getTask() {
        return new Task(
                title.getText(),
                Optional.of(description.getText()).filter(text -> !text.isBlank()).orElse(null),
                Optional.ofNullable(timestamp.getValue()).map(Timestamp::of).orElse(null),
                tags,
                isDone.isSelected(),
                contacts
        );
    }
}
