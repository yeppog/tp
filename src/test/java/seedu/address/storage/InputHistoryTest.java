package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InputHistoryTest {

    @Test
    public void correct_sequence_returnsCorrectString() {
        InputHistory history = new InputHistory();
        history.pushCommand("test 1");
        history.pushCommand("test 2");
        history.pushCommand("test 3");
        // navigate backwards
        String previous = history.getHistoryString(false, "").orElse("");
        assertEquals(previous, "test 3");
        previous = history.getHistoryString(false, "").orElse("");
        assertEquals(previous, "test 2");
        previous = history.getHistoryString(false, "").orElse("");
        assertEquals(previous, "test 1");
        previous = history.getHistoryString(false, "").orElse("");
        assertEquals(previous, "test 1");
    }

    @Test
    public void persist_lastInput_returnsCorrectString() {
        InputHistory history = new InputHistory();
        history.pushCommand("test 1");
        history.pushCommand("test 2");
        history.pushCommand("test 3");
        String lastInput = ("this is the last input");
        String previous = history.getHistoryString(false, lastInput).orElse("");
        assertEquals(previous, "test 3");
        previous = history.getHistoryString(true, lastInput).orElse("");
        assertEquals(previous, "this is the last input");
        previous = history.getHistoryString(true, lastInput).orElse("");
        previous = history.getHistoryString(true, lastInput).orElse("");
        previous = history.getHistoryString(true, lastInput).orElse("");
        previous = history.getHistoryString(true, lastInput).orElse("");
        assertEquals(previous, "this is the last input");
    }

    @Test
    public void does_notOverflowStart_returnsCorrectString() {

        InputHistory history = new InputHistory();
        history.pushCommand("test 1");
        history.pushCommand("test 2");
        history.pushCommand("test 3");
        String previous = history.getHistoryString(false, "").orElse("");
        previous = history.getHistoryString(false, "").orElse("");
        previous = history.getHistoryString(false, "").orElse("");
        previous = history.getHistoryString(false, "").orElse("");
        previous = history.getHistoryString(false, "").orElse("");
        previous = history.getHistoryString(false, "").orElse("");
        assertEquals(previous, "test 1");
    }

}
