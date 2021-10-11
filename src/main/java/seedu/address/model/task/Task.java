package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

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
    private boolean done;

    /**
     * Creates a task with a given title, and optionally a description, timestamp and a set of tags.
     * @param title The title of the task
     * @param description The optional description of the task
     * @param timestamp The optional timestamp of the task
     * @param tags The tags of the task
     */
    public Task(String title, String description, Timestamp timestamp, Set<Tag> tags) {
        this(title, description, timestamp, tags, false);
    }

    /**
     * Creates a task with a given title, and optionally a description, timestamp and a set of tags.
     * @param title The title of the task
     * @param description The optional description of the task
     * @param timestamp The optional timestamp of the task
     * @param tags The tags of the task
     * @param done Whether the task is done
     */
    public Task(String title, String description, Timestamp timestamp, Set<Tag> tags, boolean done) {
        requireAllNonNull(title, tags);

        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.tags.addAll(tags);
        this.done = done;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public boolean getDone() {
        return done;
    }

    /**
     * TODO Replace setter with EditTaskDescriptor once that and edit command is completed
     */
    public void setDone() {
        done = !done;
    }

    @Override
    public String toString() {
        return title;
    }
}
