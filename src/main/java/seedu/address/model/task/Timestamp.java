package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Represents a timestamp for a task.
 */
public class Timestamp {
    public static final String MESSAGE_CONSTRAINTS = "Timestamp should be in DD-MM-YYYY format";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final LocalDate timestamp;

    private Timestamp(LocalDate date) {
        this.timestamp = date;
    }

    /**
     * Creates a TimeStamp with the given string.
     * @param timestamp the string representing the timestamp
     */
    private Timestamp(String timestamp) throws ParseException {
        requireNonNull(timestamp);
        try {
            this.timestamp = LocalDate.parse(timestamp, formatter);
        } catch (DateTimeParseException pe) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }
    }

    public static Timestamp of(String timestamp) throws ParseException {
        return new Timestamp(timestamp);
    }

    public static Timestamp of(LocalDate date) {
        return new Timestamp(date);
    }

    /**
     * Factory method for mapping
     *
     * @param timestamp Timestamp string to parse.
     * @return TimeStamp object corresponding to string.
     */
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
        return this.timestamp.format(formatter);
    }

    public LocalDate getDate() {
        return this.timestamp;
    }

}
