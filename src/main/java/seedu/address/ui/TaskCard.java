package seedu.address.ui;

import java.util.Comparator;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.DoneTaskCommand;
import seedu.address.model.task.Contact;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;

public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskCard.fxml";

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
     * @param index The position of the task in the list
     */
    public TaskCard(Task task, Index index, Consumer<? super DoneTaskCommand> doneConsumer) {
        super(FXML);

        name.setText(index.getOneBased() + ".  " + task.getTitle());

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
            doneConsumer.accept(new DoneTaskCommand(index));
        });
    }

    private static String prependTimestampIcon(String text) {
        return "\uD83D\uDD52 " + text;
    }
}
