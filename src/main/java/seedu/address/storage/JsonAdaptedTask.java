package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Contact;
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
    private final List<JsonAdaptedContact> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("title") String title, @JsonProperty("description") String description,
                             @JsonProperty("timestamp") String timestamp, @JsonProperty("isDone") String isDone,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("contacts") List<JsonAdaptedContact> contacts) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.isDone = isDone;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (contacts != null) {
            this.contacts.addAll(contacts);
        }
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        title = source.getTitle();
        description = source.getDescription().orElse("null");
        timestamp = source.getTimestamp().map(Timestamp::toString).orElse("null");
        if (source.isDone()) {
            isDone = "Done";
        } else {
            isDone = "Not Done";
        }
        if (!source.getTags().isEmpty()) {
            tagged.addAll(source.getTags().stream()
                    .map(JsonAdaptedTag::new)
                    .collect(Collectors.toList()));
        }
        if (!source.getContacts().isEmpty()) {
            contacts.addAll(source.getContacts().stream()
                    .map(JsonAdaptedContact::new)
                    .collect(Collectors.toList()));
        }
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
        final Set<Tag> modelTags = new HashSet<>(taskTags);


        final List<Contact> taskContacts = new ArrayList<>();
        for (JsonAdaptedContact contact : contacts) {
            taskContacts.add(contact.toModelType());
        }
        final Set<Contact> modelContacts = new HashSet<>(taskContacts);


        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title"));
        }

        final Timestamp modelTimeStamp;
        if (timestamp.equals("null")) {
            modelTimeStamp = null;
        } else {
            try {
                modelTimeStamp = Timestamp.of(timestamp);
            } catch (ParseException pe) {
                throw new IllegalValueException(Timestamp.MESSAGE_CONSTRAINTS);
            }
        }

        final String modelDescription;
        if (description.equals("null")) {
            modelDescription = null;
        } else {
            modelDescription = description;
        }
        boolean modelIsDone;

        if (isDone.equals("Done")) {
            modelIsDone = true;
        } else if (isDone.equals("Not Done")) {
            modelIsDone = false;
        } else {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isDone"));
        }

        return new Task(title, modelDescription, modelTimeStamp, modelTags, modelIsDone, modelContacts);
    }

}
