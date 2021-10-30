package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;

import java.util.Optional;

public class CommandArgument {
    private final String name;
    private final Prefix prefix;
    private final boolean isRequired;
    private final boolean isMultiple;

    protected CommandArgument(String name, Prefix prefix, boolean isRequired, boolean isMultiple) {
        assert (name != null || !prefix.equals(PREFIX_PREAMBLE));
        this.name = name;
        this.prefix = prefix;
        this.isRequired = isRequired;
        this.isMultiple = isMultiple;
    }

    public static CommandArgument requiredSingle(Prefix prefix) {
        return requiredSingle(prefix, null);
    }

    public static CommandArgument requiredSingle(Prefix prefix, String name) {
        return new CommandArgument(name, prefix, true, false);
    }

    public static CommandArgument optionalSingle(Prefix prefix) {
        return optionalSingle(prefix, null);
    }

    public static CommandArgument optionalSingle(Prefix prefix, String name) {
        return new CommandArgument(name, prefix, false, false);
    }

    public static CommandArgument optionalMultiple(Prefix prefix) {
        return optionalMultiple(prefix, null);
    }

    public static CommandArgument optionalMultiple(Prefix prefix, String name) {
        return new CommandArgument(name, prefix, false, true);
    }

    public static CommandArgument unknown(Prefix prefix) {
        return new CommandArgument(null, prefix, false, false);
    }

    public static FilledCommandArgument filled(Prefix prefix, String value) {
        return new FilledCommandArgument(prefix, value);
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        String baseString = prefix.getPrefix() + Optional.ofNullable(name).orElse("");
        if (isRequired) {
            return baseString;
        } else {
            String optionalBaseString = "[" + baseString + "]";
            if (isMultiple) {
                return optionalBaseString + "...";
            } else {
                return optionalBaseString;
            }
        }
    }
}
