package seedu.address.model.task;

import java.sql.Time;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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
        requireNonNull(timestamp);
        checkArgument(isValidTimeStamp(timestamp), MESSAGE_CONSTRAINTS);
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

    private static Timestamp getToday() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Timestamp today = new Timestamp(date.toString());
        assert today.toString().length() == 10;
        return today;
    }

    public static boolean checkIsOverdue(Timestamp time) {
        return false;
    }

}
