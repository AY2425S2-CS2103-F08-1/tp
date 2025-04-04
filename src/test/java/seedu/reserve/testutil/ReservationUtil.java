package seedu.reserve.testutil;

import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DINERS;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_OCCASION;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;

import seedu.reserve.logic.commands.AddCommand;
import seedu.reserve.logic.commands.EditCommand;
import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.Reservation;

/**
 * A utility class for Reservation.
 */
public class ReservationUtil {

    /**
     * Returns an add command string for adding the {@code reservation}.
     */
    public static String getAddCommand(Reservation reservation) {
        return AddCommand.COMMAND_WORD + " " + getReservationDetails(reservation);
    }

    /**
     * Returns the part of command string for the given {@code reservation}'s details.
     */
    public static String getReservationDetails(Reservation reservation) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + reservation.getName().fullName + " ");
        sb.append(PREFIX_PHONE + reservation.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + reservation.getEmail().value + " ");
        sb.append(PREFIX_NUMBER_OF_DINERS + reservation.getDiners().value + " ");
        sb.append(PREFIX_DATE_TIME + reservation.getDateTime().toString() + " ");
        reservation.getOccasions().stream().forEach(
            s -> sb.append(PREFIX_OCCASION + s.occasionName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditReservationDescriptor}'s details.
     */
    public static String getEditReservationDescriptorDetails(EditCommand.EditReservationDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME)
                .append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE)
                .append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL)
                .append(email.value).append(" "));
        descriptor.getDiners().ifPresent(diner -> sb.append(PREFIX_NUMBER_OF_DINERS)
                .append(diner.value).append(" "));
        descriptor.getDateTime().ifPresent(dateTime -> sb.append(PREFIX_DATE_TIME)
                .append(dateTime.toString()).append(" "));
        if (descriptor.getOccasions().isPresent()) {
            Set<Occasion> occasions = descriptor.getOccasions().get();
            if (occasions.isEmpty()) {
                sb.append(PREFIX_OCCASION);
            } else {
                occasions.forEach(s -> sb.append(PREFIX_OCCASION).append(s.occasionName).append(" "));
            }
        }
        return sb.toString();
    }
}
