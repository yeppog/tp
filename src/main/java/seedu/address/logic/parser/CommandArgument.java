package seedu.address.logic.parser;

public class CommandArgument {
    private final Prefix prefix;
    private final boolean isRequired;
    private final boolean isMultiple;

    private CommandArgument(Prefix prefix, boolean isRequired, boolean isMultiple) {
        this.prefix = prefix;
        this.isRequired = isRequired;
        this.isMultiple = isMultiple;
    }

    public static CommandArgument requiredSingle(Prefix prefix) {
        return new CommandArgument(prefix, true, false);
    }

    public static CommandArgument optionalSingle(Prefix prefix) {
        return new CommandArgument(prefix, false, false);
    }

    public static CommandArgument optionalMultiple(Prefix prefix) {
        return new CommandArgument(prefix, false, true);
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public boolean isMultiple() {
        return isMultiple;
    }
}
