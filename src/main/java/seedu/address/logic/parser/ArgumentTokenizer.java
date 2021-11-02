package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PREAMBLE;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import seedu.address.logic.parser.exceptions.ArgumentContainsSlashException;
import seedu.address.logic.parser.exceptions.MissingCommandArgumentException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.TooManyPrefixesException;
import seedu.address.logic.parser.exceptions.UnwantedCommandArgumentException;
import seedu.address.logic.parser.exceptions.UnwantedPreambleException;

/**
 * Tokenizes arguments string of the form: {@code preamble <prefix>value <prefix>value ...}<br>
 * e.g. {@code some preamble text t/ 11.00 t/12.00 k/ m/ July}  where prefixes are {@code t/ k/ m/}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code k/} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 * in the above example.<br>
 */
public class ArgumentTokenizer {
    private final CommandSpecification specs;
    private final List<CommandArgument> argumentList;

    /**
     * Creates an ArgumentTokenizer that parses arguments specified by the given command specifications,
     *
     * @param specs The command specifications to parse arguments according to
     */
    public ArgumentTokenizer(CommandSpecification specs) {
        this.specs = specs;
        argumentList = specs.getCommandArguments();
    }

    private CommandArgument getCommandArgumentWithPrefix(Prefix prefix) {
        return Optional.ofNullable(
                specs.getCommandArgumentWithPrefix(prefix)).orElseGet(() -> CommandArgument.unknown(prefix));
    }

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps prefixes to their
     * respective argument values. All character sequences of the form "abc/def" will be recognized as a prefix
     * along with its argument. A prefix is a non-empty alphabetical sequence, while an argument to a prefix
     * is the trimmed portion of text from the end of the prefix until the start of the next prefix.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @return ArgumentMultimap object that maps prefixes to their arguments
     */
    public ArgumentMultimap tokenize(String argsString) throws ParseException {
        Matcher matcher = CliSyntax.PREFIX_PATTERN.matcher(argsString);
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();

        int end = 0;
        int preambleEnd = argsString.length();
        while (matcher.find(end)) {
            if (end == 0) {
                preambleEnd = matcher.start(1);
            }
            end = matcher.end(1);

            String prefixAndValue = matcher.group(1);

            int firstSlashIndex = prefixAndValue.indexOf("/");
            assert firstSlashIndex != -1;

            String prefixString = prefixAndValue.substring(0, firstSlashIndex + 1);
            String value = prefixAndValue.substring(firstSlashIndex + 1).trim();


            Prefix prefix = new Prefix(prefixString);
            if (value.contains("/")) {
                throw new ArgumentContainsSlashException(getCommandArgumentWithPrefix(prefix), specs);
            }
            argumentMultimap.put(prefix, value);
        }

        // Check for missing arguments
        Optional<Prefix> missingPrefix = argumentList.stream()
                .filter(CommandArgument::isRequired)
                .map(CommandArgument::getPrefix)
                .filter(prefix -> !prefix.equals(PREFIX_PREAMBLE))
                .filter(p -> !argumentMultimap.contains(p))
                .findAny();
        if (missingPrefix.isPresent()) {
            throw new MissingCommandArgumentException(getCommandArgumentWithPrefix(missingPrefix.get()), specs);
        }

        String preamble = argsString.substring(0, preambleEnd).trim();

        if (preamble.contains("/")) {
            throw new ArgumentContainsSlashException(getCommandArgumentWithPrefix(PREFIX_PREAMBLE), specs);
        }

        // Check for unwanted preamble
        if (!preamble.isEmpty()) {
            boolean isPreambleExpected = argumentList.stream().map(CommandArgument::getPrefix)
                    .anyMatch(p -> p.equals(PREFIX_PREAMBLE));
            if (!isPreambleExpected) {
                throw new UnwantedPreambleException(preamble, specs);
            }
            argumentMultimap.put(PREFIX_PREAMBLE, preamble);
        } else {
            boolean isPreambleRequired = argumentList.stream()
                    .filter(CommandArgument::isRequired)
                    .anyMatch(a -> a.getPrefix()
                            .equals(PREFIX_PREAMBLE));
            // Check for missing preamble
            if (isPreambleRequired) {
                throw new MissingCommandArgumentException(getCommandArgumentWithPrefix(PREFIX_PREAMBLE), specs);
            }
        }

        // Check for unwanted arguments
        for (Prefix prefix : argumentMultimap.getPrefixes()) {
            Optional<CommandArgument> argument = argumentList.stream().filter(a -> a.getPrefix().equals(prefix))
                    .findFirst();
            if (argument.isEmpty()) {
                // Argument not in expected list
                throw new UnwantedCommandArgumentException(getCommandArgumentWithPrefix(prefix), specs);
            } else {
                if (!argument.get().isMultiple() && argumentMultimap.getAllValues(prefix).size() > 1) {
                    throw new TooManyPrefixesException(getCommandArgumentWithPrefix(prefix), specs);
                }
            }
        }

        return argumentMultimap;
    }
}
