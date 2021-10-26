package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.task.Contact;

/**
 * Jackson-friendly version of {@link Contact}.
 */
class JsonAdaptedContact {

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code contactName}.
     */
    @JsonCreator
    public JsonAdaptedContact(String tagName) {
        this.name = tagName;
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        name = source.getName().fullName;
    }

    @JsonValue
    public String getContactName() {
        return name;
    }

    /**
     * Converts this Jackson-friendly adapted Contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Contact.
     */
    public Contact toModelType() throws IllegalValueException {
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Contact(new Name(name));
    }

}