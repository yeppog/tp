package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exists TaskMaster2103. "
            + "Parameters: none";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting TaskMaster2103 as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
