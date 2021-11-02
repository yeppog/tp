package seedu.address.logic.parser;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.parser.exceptions.InvalidCommandArgumentFormatException;
import seedu.address.logic.parser.exceptions.ParseException;

@FunctionalInterface
public interface ArgumentParser<S, T> {
    T parse(S argString) throws ParseException;


    /**
     * Parses a value from the given argument multimap matching the given prefix, supplied to the command specified by
     * {@code specs}, using the given parser.
     * Returns null if the value was not provided as an argument.
     *
     * @param prefix The prefix to obtain the argument from
     * @param parser The function used to parse the function
     * @param argMultimap The argument multimap containing all parsed arguments
     * @param specs The specifications of the command to which the value is an argument of
     * @param <T> The type of the parsed argument
     * @return a parsed value representing the parsed argument
     * @throws ParseException If the parse throws a {@link ParseException}
     */
    static <T> T parseValue(
            Prefix prefix,
            ArgumentParser<? super String, ? extends T> parser,
            ArgumentMultimap argMultimap,
            CommandSpecification specs
    ) throws ParseException {
        try {
            assert argMultimap.contains(prefix);
            return parser.parse(argMultimap.getValue(prefix).orElseThrow());
        } catch (ParseException e) {
            throw new InvalidCommandArgumentFormatException(specs.getCommandArgumentWithPrefix(prefix), e.getMessage(),
                    specs);
        }
    }

    /**
     * Parses an optional value from the given argument multimap matching the given prefix, supplied to the command
     * specified by
     * {@code specs}, using the given parser.
     * Returns null if the value was not provided as an argument.
     *
     * @param prefix The prefix to obtain the argument from
     * @param parser The function used to parse the function
     * @param argMultimap The argument multimap containing all parsed arguments
     * @param specs The specifications of the command to which the value is an argument of
     * @param <T> The type of the parsed argument
     * @return an optional parsed value representing the parsed argument
     * @throws ParseException If the parse throws a {@link ParseException}
     */
    static <T> Optional<T> parseOptionalValue(
            Prefix prefix,
            ArgumentParser<? super String, ? extends T> parser,
            ArgumentMultimap argMultimap,
            CommandSpecification specs
    ) throws ParseException {
        try {
            if (argMultimap.getValue(prefix).isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(parser.parse(argMultimap.getValue(prefix).get()));
            }
        } catch (ParseException e) {
            throw new InvalidCommandArgumentFormatException(specs.getCommandArgumentWithPrefix(prefix),
                    e.getMessage(), specs);
        }
    }


    /**
     * Parses all values from the given argument multimap matching the given prefix, supplied to the command
     * specified by {@code specs}, using the given parser.
     * Returns null if the value was not provided as an argument.
     *
     * @param prefix The prefix to obtain the argument from
     * @param parser The function used to parse the function
     * @param argMultimap The argument multimap containing all parsed arguments
     * @param specs The specifications of the command to which the value is an argument of
     * @param <T> The type of the parsed argument
     * @return a value representing the parsed arguments
     * @throws ParseException If the parse throws a {@link ParseException}
     */
    static <T> T parseAllValues(
            Prefix prefix,
            ArgumentParser<? super List<String>, ? extends T> parser,
            ArgumentMultimap argMultimap,
            CommandSpecification specs
    ) throws ParseException {
        try {
            return parser.parse(argMultimap.getAllValues(prefix));
        } catch (ParseException e) {
            throw new InvalidCommandArgumentFormatException(specs.getCommandArgumentWithPrefix(prefix),
                    e.getMessage(), specs);
        }
    }
}
