package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;

import java.util.Optional;

public class CommandArgument {
    private final String name;
    private final Prefix prefix;
    private final boolean isRequired;
    private final boolean isMultiple;

    protected CommandArgument(Prefix prefix, String name, boolean isRequired, boolean isMultiple) {
        assert (name != null || !prefix.equals(PREFIX_PREAMBLE));
        this.name = name;
        this.prefix = prefix;
        this.isRequired = isRequired;
        this.isMultiple = isMultiple;
    }

    /**
     * Represents a required single argument.
     */
    public static CommandArgument requiredSingle(Prefix prefix) {
        return requiredSingle(prefix, null);
    }

    /**
     * Represents a required single argument with a given argument name.
     * For example: tag/TAG; tag/ is the prefix, TAG is the name.
     */
    public static CommandArgument requiredSingle(Prefix prefix, String name) {
        return new CommandArgument(prefix, name, true, false);
    }

    /**
     * Represents a optional single argument.
     */
    public static CommandArgument optionalSingle(Prefix prefix) {
        return optionalSingle(prefix, null);
    }

    /**
     * Represents a optional single argument with a given argument name.
     * For example: tag/TAG; tag/ is the prefix, TAG is the name.
     */
    public static CommandArgument optionalSingle(Prefix prefix, String name) {
        return new CommandArgument(prefix, name, false, false);
    }

    /**
     * Represents a optional multiple argument.
     */
    public static CommandArgument optionalMultiple(Prefix prefix) {
        return optionalMultiple(prefix, null);
    }

    /**
     * Represents a optional multiple argument with a given argument name.
     * For example: tag/TAG; tag/ is the prefix, TAG is the name.
     */
    public static CommandArgument optionalMultiple(Prefix prefix, String name) {
        return new CommandArgument(prefix, name, false, true);
    }

    /**
     * Represents a prefix with unknown multiplicity. Used for extra prefixes
     * that are not recognized by a given command.
     */
    public static CommandArgument unknown(Prefix prefix) {
        return new CommandArgument(prefix, null, false, false);
    }

    /*
     * Represents a command argument filled with a certain value.
     */
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
