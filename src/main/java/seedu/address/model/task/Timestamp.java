package seedu.address.model.task;


import java.time.LocalDate;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a timestamp for a task.
 */
public class Timestamp {
    private final String timestamp;
    private static final String VALIDATION_REGEX = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
            + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";
    public static final String MESSAGE_CONSTRAINTS = "Timestamp should be in YYYY-MM-DD format";

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

    private static LocalDate getToday() {

        return LocalDate.now();
    }

    public static boolean isValidTimeStamp(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean checkIsOverdue(Timestamp time) {
        int[] timeArray = Arrays.stream(time.toString().split("-")).mapToInt(Integer::parseInt).toArray();
        LocalDate timestamp = LocalDate.of(timeArray[0], timeArray[1], timeArray[2]);

        return getToday().isAfter(timestamp);
    }

}
