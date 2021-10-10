package seedu.address.testutil;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

public class TaskBuilder {
    public static final String DEFAULT_TITLE = "Buy groceries";
    public static final String DEFAULT_DESCRIPTION = "Two eggs, one carton of milk and five tomatoes";
    public static final Timestamp DEFAULT_TIMESTAMP = new Timestamp("Tomorrow");
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();
    public static final boolean DEFAULT_IS_DONE = false;


    private String title;
    private String description;
    private Timestamp timestamp;
    private Set<Tag> tags;
    private boolean isDone;

    public TaskBuilder() {
        this.title = DEFAULT_TITLE;
        this.description = DEFAULT_DESCRIPTION;
        this.timestamp = DEFAULT_TIMESTAMP;
        this.tags = DEFAULT_TAGS;
        this.isDone = DEFAULT_IS_DONE;
    }

    public TaskBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder withTimestamp(String timestamp) {
        this.timestamp = new Timestamp(timestamp);
        return this;
    }

    public TaskBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public TaskBuilder withDone(boolean done) {
        this.isDone = done;
        return this;
    }

    public Task build() {
        return new Task(title, description, timestamp, tags, isDone);
    }
}
