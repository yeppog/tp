package seedu.address.model.task;

import seedu.address.model.tag.Tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

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

    public Task(String title, String description, Timestamp timestamp, Set<Tag> tags) {
        requireAllNonNull(title, tags);

        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.tags.addAll(tags);
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
}
