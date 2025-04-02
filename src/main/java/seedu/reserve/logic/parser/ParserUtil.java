package seedu.reserve.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javafx.util.Pair;
import seedu.reserve.commons.core.index.Index;
import seedu.reserve.commons.util.StringUtil;
import seedu.reserve.logic.commands.ClearCommand;
import seedu.reserve.logic.commands.DeleteCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "The reservation index provided is invalid";
    public static final String MESSAGE_NEGATIVE_INDEX =
        "The reservation index must be a non-negative integer greater than 0!";
    private static final String CONFIRMATION_KEYWORD = "cfm";
    private static final int INDEX_POSITION = 0; // Index is expected at position 0
    private static final int CONFIRMATION_POSITION = 1; // "confirm" keyword is expect at position 1
    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses the given {@code args} string for a delete command and extracts the index
     * and confirmation flag.
     *
     * @param args The input string containing the index and optional confirmation keyword.
     * @return A {@code Pair} containing the parsed {@code Index} and a {@code Boolean}
     *         indicating whether the delete action is confirmed.
     * @throws ParseException If the input format is invalid or the index is not a valid number.
     */
    public static Pair<Index, Boolean> parseDeleteArgs(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (!DeleteCommand.isValidDelete(trimmedArgs)) {
            throw new ParseException(DeleteCommand.MESSAGE_USAGE);
        }

        String[] splitArgs = trimmedArgs.split("\\s+");
        assert splitArgs.length > 0 : "splitArgs should have at least one element";

        Index index = parseIndex(splitArgs[INDEX_POSITION]);
        boolean isConfirmed = splitArgs.length == 2 && splitArgs[CONFIRMATION_POSITION].equals(CONFIRMATION_KEYWORD);

        return new Pair<>(index, isConfirmed);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String occasion} into a {@code Occasion}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code occasion} is invalid.
     */
    public static Occasion parseOccasion(String occasion) throws ParseException {
        requireNonNull(occasion);
        String trimmedOccasion = occasion.trim();
        if (!Occasion.isValidOccasionName(trimmedOccasion)) {
            throw new ParseException(Occasion.MESSAGE_OCCASION_CONSTRAINTS);
        }
        return new Occasion(trimmedOccasion);
    }

    /**
     * Parses {@code Collection<String> occasions} into a {@code Set<Occasion>}.
     */
    public static Set<Occasion> parseOccasions(Collection<String> occasions) throws ParseException {
        requireNonNull(occasions);
        final Set<Occasion> occasionSet = new HashSet<>();
        for (String occasionName : occasions) {
            occasionSet.add(parseOccasion(occasionName));
        }
        return occasionSet;
    }

    /**
     * Parses a {@code String diners} into a {@code Diners}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code diners} is invalid.
     */
    public static Diners parseDiners(String diners) throws ParseException {
        requireNonNull(diners);
        String trimmedDiners = diners.trim();
        try {
            String numberOfDiners = trimmedDiners;
            if (!Diners.isValidDiners(numberOfDiners)) {
                throw new ParseException(Diners.MESSAGE_CONSTRAINTS);
            }
            return new Diners(numberOfDiners);
        } catch (NumberFormatException e) {
            throw new ParseException(Diners.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String dateTime} into a {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        return new DateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code String dateTime} into a {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseDateTimeFree(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS_FREE);
        }
        return new DateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code String dateTime} into a {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseEditedDateTime(String editedDateTime) throws ParseException {
        requireNonNull(editedDateTime);
        String trimmedEditDateTime = editedDateTime.trim();
        return DateTime.fromFileString(trimmedEditDateTime);
    }


    /**
     * Parses a {@code dateTimeFile} to a {@code  DateTime}. Does not check if {@code dateTimeFile}
     * is after today's date.
     *
     * @param dateTimeFile
     * @return DateTime object of {@code dateTimeFile}
     * @throws ParseException if the given {@code dateTimeFile} is invalid
     */
    public static DateTime parseDateTimeFilter(String dateTimeFile) throws ParseException {
        requireNonNull(dateTimeFile);
        String trimmedDateTime = dateTimeFile.trim();

        if (!DateTime.isValidFileInputDateTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS_FILTER);
        }

        DateTime dateTime;

        try {
            dateTime = DateTime.fromFileString(trimmedDateTime);
        } catch (DateTimeException e) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS_FILTER);
        }

        return dateTime;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given input string and determines if the clear command is confirmed.
     *
     * @param args The user input string to be parsed.
     * @return {@code true} if the user input matches the confirmation keyword, {@code false} otherwise.
     * @throws ParseException If the input does not match the expected confirmation format.
     */
    public static boolean parseClearArgs(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (!ClearCommand.isValidConfirm(trimmedArgs)) {
            throw new ParseException(ClearCommand.MESSAGE_USAGE);
        }

        boolean isConfirmed = trimmedArgs.equals(CONFIRMATION_KEYWORD);

        return isConfirmed;
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parsePrefIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_NEGATIVE_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }
}
