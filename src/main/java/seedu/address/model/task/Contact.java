package seedu.address.model.task;

import seedu.address.model.person.Name;

/**
 * Contact is a person related to a task, who may or may not be present in the Address Book.
 */
public class Contact {
    private final Name name;

    private boolean isInAddressBook;

    /**
     * Creates a Name with the given string.
     * @param name the string representing the contact's name
     */
    public Contact(Name name) {
        this.name = name;
        isInAddressBook = false;
    }

    public Name getName() {
        return name;
    }

    public boolean getIsInAddressBook() {
        return isInAddressBook;
    }

    public void setInAddressBook(boolean isInAddressBook) {
        this.isInAddressBook = isInAddressBook;
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
