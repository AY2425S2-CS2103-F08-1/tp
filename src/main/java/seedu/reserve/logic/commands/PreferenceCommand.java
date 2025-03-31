package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.Preference;
import seedu.reserve.model.reservation.Reservation;

/**
 * Saves or shows customer preferences for a reservation identified using its displayed index.
 */
public class PreferenceCommand extends Command {

    public static final String COMMAND_WORD = "pref";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Saves or shows customer preferences for the reservation identified by the index number.\n"
        + "Parameters for saving: " + COMMAND_WORD + " save INDEX PREFERENCE\n"
        + "Parameters for showing: " + COMMAND_WORD + " show INDEX\n"
        + "Example: " + COMMAND_WORD + " save 1 No nuts, allergic to seafood\n"
        + "Example: " + COMMAND_WORD + " show 1";

    public static final String MESSAGE_SAVE_PREFERENCE_SUCCESS = "Saved preference for reservation: %1$s";
    public static final String MESSAGE_SHOW_PREFERENCE_SUCCESS = "Preference for reservation %1$s: %2$s";
    public static final String MESSAGE_NO_PREFERENCE = "No preference has been set for this reservation.";
    public static final String MESSAGE_INVALID_INDEX = "Reservation at the specified index cannot be null";

    private final Index index;
    private final boolean isShow;
    private final Preference preference;

    /**
     * Constructor for showing preference.
     */
    public PreferenceCommand(Index index, boolean isShow) {
        requireNonNull(index);
        this.index = index;
        this.isShow = isShow;
        this.preference = null;
    }

    /**
     * Constructor for saving preference.
     */
    public PreferenceCommand(Index index, String preferenceText) {
        requireAllNonNull(index, preferenceText);
        this.index = index;
        this.isShow = false;
        this.preference = new Preference(preferenceText);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX,
                index.getOneBased()));
        }

        Reservation reservationToEdit = lastShownList.get(index.getZeroBased());
        assert reservationToEdit != null : MESSAGE_INVALID_INDEX;

        if (isShow) {
            return executeShowPreference(reservationToEdit);
        } else {
            return executeSavePreference(model, reservationToEdit);
        }
    }

    /**
     * Executes the show preference functionality.
     * @param reservation The reservation to show preference for
     * @return CommandResult containing the preference message
     */
    private CommandResult executeShowPreference(Reservation reservation) {
        Preference reservationPreference = reservation.getPreference();
        if (reservationPreference.isEmpty()) {
            return new CommandResult(MESSAGE_NO_PREFERENCE);
        }
        return new CommandResult(String.format(MESSAGE_SHOW_PREFERENCE_SUCCESS,
            index.getOneBased(), reservationPreference.toString()));
    }

    /**
     * Executes the save preference functionality.
     * @param model The model to update
     * @param reservation The reservation to save preference for
     * @return CommandResult indicating success
     */
    private CommandResult executeSavePreference(Model model, Reservation reservation) throws CommandException {
        // Create a new reservation with the updated preference
        Reservation updatedReservation = createUpdatedReservation(reservation);
        // Update the model
        model.setReservation(reservation, updatedReservation);
        // Return success message with the index
        return new CommandResult(String.format(MESSAGE_SAVE_PREFERENCE_SUCCESS, index.getOneBased()));
    }
    /**
     * Creates and returns a {@code Reservation} with the updated preference.
     */
    private Reservation createUpdatedReservation(Reservation reservationToEdit) {
        assert reservationToEdit != null : MESSAGE_INVALID_INDEX;
        return new Reservation(
                reservationToEdit.getName(),
                reservationToEdit.getPhone(),
                reservationToEdit.getEmail(),
                reservationToEdit.getDiners(),
                reservationToEdit.getDateTime(),
                reservationToEdit.getOccasions(),
                preference);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PreferenceCommand)) {
            return false;
        }

        PreferenceCommand otherCommand = (PreferenceCommand) other;
        return index.equals(otherCommand.index)
            && isShow == otherCommand.isShow
            && (preference == null ? otherCommand.preference == null
            : preference.equals(otherCommand.preference));
    }
}
