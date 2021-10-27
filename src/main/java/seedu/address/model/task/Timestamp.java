package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Represents a timestamp for a task.
 */
public class Timestamp {
    public static final String MESSAGE_CONSTRAINTS = "Timestamp should be in YYYY-MM-DD format";

    private final LocalDate timestamp;

    /**
     * Creates a TimeStamp with the given string.
     * @param timestamp the string representing the timestamp
     */
    private Timestamp(String timestamp) throws ParseException {
        requireNonNull(timestamp);
        try {
            this.timestamp = LocalDate.parse(timestamp);
        } catch (DateTimeParseException pe) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }
    }

    public static Timestamp of(String timestamp) throws ParseException {
        return new Timestamp(timestamp);
    }

    public static Timestamp tryParse(String timestamp) {
        try {
            return new Timestamp(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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
        return this.timestamp.toString();
    }

    public LocalDate getTimestamp() {
        return this.timestamp;
    }

}
