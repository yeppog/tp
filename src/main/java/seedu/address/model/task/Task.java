package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.model.TaskList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the task list.
 * Guarantees: title is present and not null, field values are validated, immutable.
 */
public class Task {
    private final String title;

    // Optional description fields
    private final String description;
    private final Timestamp timestamp;
    private final Set<Tag> tags = new HashSet<>();
    private final boolean isDone;
    private final Set<Contact> contacts = new HashSet<>();

    /**
     * Creates a task with a given title, and optionally a description, timestamp and a set of tags.
     * @param title The title of the task
     * @param description The optional description of the task
     * @param timestamp The optional timestamp of the task
     * @param tags The tags of the task
     */
    public Task(String title, String description, Timestamp timestamp, Set<Tag> tags, Set<Contact> contacts) {
        this(title, description, timestamp, tags, false, contacts);
    }

    /**
     * Creates a task with a given title, and optionally a description, timestamp and a set of tags.
     * Additionally, it also takes in the completion status of the task.
     * @param title The title of the task
     * @param description The optional description of the task
     * @param timestamp The optional timestamp of the task
     * @param tags The tags of the task
     * @param isDone The completion status of the task
     */
    public Task(String title, String description, Timestamp timestamp,
                Set<Tag> tags, boolean isDone, Set<Contact> contacts) {
        requireAllNonNull(title, tags, contacts);
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.tags.addAll(tags);
        this.isDone = isDone;
        this.contacts.addAll(contacts);
    }


    public String getTitle() {
        return this.title;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Timestamp> getTimestamp() {
        return Optional.ofNullable(timestamp);
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Checks if timestamp of task occurs before local date.
     *
     * @return boolean True if task is overdue
     */
    public boolean isOverdue() {
        if (timestamp == null) {
            return false;
        } else {
            return LocalDate.now().isAfter(timestamp.getDate());
        }
    }

    public boolean isDone() {
        return isDone;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }


    /**
     * Returns whether this task has the same fields as the other task.
     * Used by {@link Command} and {@link TaskList} instances to determine
     * attribute equivalence in tasks.
     *
     * @param otherTask The task to compared with for deep equality
     * @return true if the task has deeply equal to the given task
     */
    public boolean deepEquals(Task otherTask) {
        return Objects.equals(title, otherTask.title)
                && Objects.equals(description, otherTask.description)
                && Objects.equals(timestamp, otherTask.timestamp)
                && Objects.equals(tags, otherTask.tags)
                && Objects.equals(isDone, otherTask.isDone)
                && Objects.equals(contacts, otherTask.contacts);
    }

    @Override
    public String toString() {
        return this.title;
    }
}
