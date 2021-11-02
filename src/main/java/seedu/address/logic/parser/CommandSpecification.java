package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandSpecification {
    private final String word;
    private final String description;
    private final List<CommandArgument> commandArguments;
    private final List<String> exampleCommands;

    /**
     * Creates a specification of a command, with the word used to invoke the command, a description of the command,
     * and a list of arguments to be supplied to the command.
     *
     * @param word The word used to invoke the command
     * @param description The description of the command
     * @param commandArguments The arguments to be supplied to the command
     */
    public CommandSpecification(String word, String description, CommandArgument... commandArguments) {
        this.word = word;
        this.description = description;
        this.commandArguments = List.of(commandArguments);
        this.exampleCommands = new ArrayList<>();
    }

    /**
     * Returns the command specification with an example of a command execution added to it.
     *
     * @param filledCommandArguments A list of filled arguments representing the example command
     * @return The same command specification, with an example of a command execution added to it
     */
    public CommandSpecification withExample(FilledCommandArgument... filledCommandArguments) {
        String exampleCommand = word + " " + Arrays.stream(filledCommandArguments)
                        .map(FilledCommandArgument::toString)
                        .collect(Collectors.joining(" "));
        exampleCommands.add(exampleCommand);
        return this;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

    public List<CommandArgument> getCommandArguments() {
        return commandArguments;
    }

    public List<String> getExampleCommands() {
        return exampleCommands;
    }

    public CommandArgument getCommandArgumentWithPrefix(Prefix prefix) {
        return commandArguments.stream()
                .filter(argument -> argument.getPrefix().equals(prefix)).findFirst().orElse(null);
    }

    public String getCommandUsage() {
        return word + " " + getCommandArguments().stream().map(CommandArgument::toString)
                .collect(Collectors.joining(" "));
    }

    public String getUsageMessage() {
        List<String> lines = new ArrayList<>();
        lines.add(word + ": " + description);
        lines.add(getCommandUsage());
        if (!exampleCommands.isEmpty()) {
            lines.add("Examples:");
            lines.addAll(exampleCommands);
        }
        return String.join("\n", lines);
    }
}
