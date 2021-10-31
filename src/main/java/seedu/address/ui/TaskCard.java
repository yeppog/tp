package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.Contact;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;
import seedu.address.ui.exceptions.GuiException;

public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskCard.fxml";
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    @FXML
    private Label name;

    @FXML
    private Label timestamp;

    @FXML
    private Label description;

    @FXML
    private FlowPane tags;

    @FXML
    private CheckBox isCompleted;

    @FXML
    private FlowPane contacts;

    /**
     * Creates a card representing a task. Used in a task list to display a task.
     * @param task The task to represent
     * @param oneIndex The position of the task in the list in one-based indexing
     */
    public TaskCard(Task task, int oneIndex, TaskListPanel.TaskEditor taskEditor) {
        super(FXML);

        name.setText(oneIndex + ".  " + task.getTitle());

        if (task.getDescription().isEmpty()) {
            description.setVisible(false);
            description.setManaged(false);
        } else {
            description.setText(task.getDescription().get());
        }

        if (task.getTags().isEmpty()) {
            tags.setVisible(false);
            tags.setManaged(false);
        } else {
            task.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .map(tag -> new Label(tag.tagName))
                    .forEach(tags.getChildren()::add);

            // Set the max width of the tag container related to the width of the task card
            tags.prefWrapLengthProperty().bind(getRoot().widthProperty().divide(1.5));
        }

        if (task.getTimestamp().isEmpty()) {
            timestamp.setVisible(false);
            timestamp.setManaged(false);
        } else {
            String text = task.getTimestamp()
                        .map(Timestamp::toString)
                        .map(TaskCard::prependTimestampIcon)
                        .orElse("");
            timestamp.setText(text);
            if (task.isOverdue()) {
                logger.info(Boolean.toString(task.isOverdue()));
                timestamp.getStyleClass().add("overdue");
            }
        }

        if (task.getContacts().isEmpty()) {
            contacts.setVisible(false);
            contacts.setManaged(false);
        } else {
            task.getContacts().stream()
                    .filter(Contact::isInAddressBook)
                    .sorted(Comparator.comparing(contact -> contact.getName().fullName))
                    .map(contact -> new Label(contact.getName().fullName))
                    .forEach(contacts.getChildren()::add);

            task.getContacts().stream()
                    .filter(contact -> !contact.isInAddressBook())
                    .sorted(Comparator.comparing(contact -> contact.getName().fullName))
                    .map(contact -> {
                        Label label = new Label(contact.getName().fullName);
                        label.getStyleClass().add("notIn");
                        return label;
                    })
                    .forEach(contacts.getChildren()::add);

            // Set the max width of the tag container related to the width of the task card
            contacts.prefWrapLengthProperty().bind(getRoot().widthProperty().divide(1.5));
        }

        isCompleted.setText("");
        isCompleted.setSelected(task.isDone());
        isCompleted.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            Task newTask = new Task(
                task.getTitle(),
                task.getDescription().orElse(null),
                task.getTimestamp().orElse(null),
                task.getTags(),
                newValue,
                task.getContacts()
            );
            try {
                taskEditor.updateTask(task, newTask);
            } catch (GuiException e) {
                e.printStackTrace();
            }
        });


    }

    private static String prependTimestampIcon(String text) {
        return "\uD83D\uDD52 " + text;
    }
}
