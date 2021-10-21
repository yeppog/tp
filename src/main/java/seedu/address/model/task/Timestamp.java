package seedu.address.model.task;

/**
 * Represents a timestamp for a task.
 */
public class Timestamp {
    private final String timestamp;

    /**
     * Creates a TimeStamp with the given string.
     * @param timestamp the string representing the timestamp
     */
    public Timestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timestamp // instanceof handles nulls
                && timestamp.equals(((Timestamp) other).timestamp));
    }

    @Override
    public int hashCode() {
        return timestamp.hashCode();
    }

    @Override
    public String toString() {
        return this.timestamp;
    }

    // TODO: Create a DateTime representation of timestamps
}
