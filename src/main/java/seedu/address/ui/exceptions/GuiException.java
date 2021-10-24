package seedu.address.ui.exceptions;

import seedu.address.logic.guiactions.GuiAction;

/**
 * Represents an error which occurs during execution of a {@link GuiAction}.
 */
public class GuiException extends Exception {
    public GuiException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code GuiException} with the specified detail {@code message} and {@code cause}.
     */
    public GuiException(String message, Throwable cause) {
        super(message, cause);
    }
}
