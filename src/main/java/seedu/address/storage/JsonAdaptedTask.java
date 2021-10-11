package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String title;
    private final String description;
    private final String timestamp;
    private final String isDone;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("title") String title, @JsonProperty("description") String description,
                             @JsonProperty("timestamp") String timestamp, @JsonProperty("isDone") String isDone,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.isDone = isDone;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        title = source.getTitle();
        description = source.getDescription();
        timestamp = source.getTimestamp().toString();
        if (source.getDone()) {
            isDone = "Done";
        } else {
            isDone = "Not Done";
        }
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title"));
        }

        final Set<Tag> modelTags = new HashSet<>(taskTags);
        final Timestamp modelTimeStamp = new Timestamp(timestamp);
        Task modelTask = new Task(title, description, modelTimeStamp, modelTags);

        if (isDone.equals("Done")) {
            modelTask.setDone();
        } else if (isDone.equals("Not Done")) {
        } else {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isDone"));
        }
        return modelTask;
    }

}
