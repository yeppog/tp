package seedu.address.logic.parser;

public class FilledCommandArgument extends CommandArgument {
    protected FilledCommandArgument(Prefix prefix, String value) {
        super(value, prefix, false, false);
    }

    @Override
    public String toString() {
        return getPrefix().getPrefix() + getName();
    }
}
