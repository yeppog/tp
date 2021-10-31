package seedu.address.model.task;

import seedu.address.model.person.Name;

/**
 * Contact is a person related to a task, who may or may not be present in the Address Book.
 */
public class Contact {
    private final Name name;

    private final boolean isInAddressBook;

    /**
     * Creates a Name with the given string.
     * @param name the string representing the contact's name
     */
    public Contact(Name name) {
        this(name, false);
    }

    /**
     * Creates a Name with the given string and the given boolean
     * @param name the string representing the contact's name
     * @param isInAddressBook Whether the contact is currently in AB3
     */
    public Contact(Name name, boolean isInAddressBook) {
        this.name = name;
        this.isInAddressBook = isInAddressBook;
    }

    public Name getName() {
        return name;
    }

    public boolean isInAddressBook() {
        return isInAddressBook;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Contact // instanceof handles nulls
                && name.equals(((Contact) other).name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
