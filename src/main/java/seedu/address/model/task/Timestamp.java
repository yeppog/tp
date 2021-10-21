package seedu.address.model.task;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timestamp timestamp1 = (Timestamp) o;
        return Objects.equals(timestamp, timestamp1.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp);
    }

    @Override
    public String toString() {
        return this.timestamp;
    }

    // TODO: Create a DateTime representation of timestamps
}
