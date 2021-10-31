package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.task.Contact;

/**
 * Jackson-friendly version of {@link Contact}.
 */
class JsonAdaptedContact {

    private final String name;
    private final Boolean isInAddressBook;

    /**
     * Constructs a {@code JsonAdaptedContact} with the given {@code name} and {@code isInAddressBook}.
     */
    @JsonCreator
    public JsonAdaptedContact(@JsonProperty("name") String name,
                              @JsonProperty("isInAddressBook") Boolean isInAddressBook) {
        this.name = name;
        this.isInAddressBook = isInAddressBook;
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        name = source.getName().fullName;
        isInAddressBook = source.isInAddressBook();
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
        return new Contact(new Name(name), isInAddressBook);
    }

}
