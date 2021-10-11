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

    // TODO: Create a DateTime representation of timestamps

    @Override
    public String toString() {
        return timestamp;
    }
}
