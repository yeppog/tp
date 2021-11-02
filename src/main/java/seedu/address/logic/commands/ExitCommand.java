package seedu.address.logic.commands;

import seedu.address.logic.parser.CommandSpecification;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand implements Command {

    public static final String COMMAND_WORD = "exit";

    public static final CommandSpecification COMMAND_SPECS = new CommandSpecification(
            COMMAND_WORD,
            "Exits TaskMaster2103."
    );

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting TaskMaster2103 as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
