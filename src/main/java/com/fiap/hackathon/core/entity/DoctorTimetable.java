package com.fiap.hackathon.core.entity;

import com.fiap.hackathon.common.exceptions.custom.ExceptionCodes;
import com.fiap.hackathon.common.exceptions.custom.TimetableSlotsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTimetable {

    private String id;
    private String doctorId;
    private Set<String> sunday;
    private Set<String> monday;
    private Set<String> tuesday;
    private Set<String> wednesday;
    private Set<String> thursday;
    private Set<String> friday;
    private Set<String> saturday;

    public Boolean isValid() throws TimetableSlotsException {
        if (!sunday.isEmpty()) {
            throw new TimetableSlotsException(
                    ExceptionCodes.USER_11_UNAVAILABLE_TIMESLOT,
                    "Sunday timeslots are not allowed yet. Please request again leaving this day slots empty."
            );
        }

        sunday = Set.of(TimeSlotsEnum.UNAVAILABLE.getSlot());

        checkTimeSlots(monday);
        checkTimeSlots(tuesday);
        checkTimeSlots(wednesday);
        checkTimeSlots(thursday);
        checkTimeSlots(friday);
        checkTimeSlots(saturday);

        return Boolean.TRUE;
    }

    private void checkTimeSlots(Set<String> timeslots) throws TimetableSlotsException {
        final var isUnavailable = containsUnavailable(timeslots);

        if (Boolean.TRUE.equals(isUnavailable)) {
            final var message = String.format("If UNAVAILABLE timeslot (%s) is informed, no other timeslot should be on the list.", TimeSlotsEnum.UNAVAILABLE.getSlot());

            throw new TimetableSlotsException(
                    ExceptionCodes.USER_11_UNAVAILABLE_TIMESLOT,
                    message
            );
        }

        for (String timeSlot : timeslots) {
            if (Boolean.FALSE.equals(TimeSlotsEnum.isValid(timeSlot))) {
                final var message = String.format("TimeSlot %s is not available. Please select times ranging from 7:00-18:00, with 1h duration max.", timeSlot);

                throw new TimetableSlotsException(
                        ExceptionCodes.USER_12_INVALID_TIMESLOT,
                        message
                );
            }
        }
    }

    private static Boolean containsUnavailable(Set<String> timeslots) {
        return timeslots.size() > 1 && timeslots.contains(TimeSlotsEnum.UNAVAILABLE.getSlot());
    }

    public static DoctorTimetable mergeUpdates(DoctorTimetable timetable, DoctorTimetable update) {

        if (update.monday != null && !update.monday.isEmpty())
            timetable.monday = update.getMonday();

        if (update.tuesday != null && !update.tuesday.isEmpty())
            timetable.tuesday = update.getTuesday();

        if (update.wednesday != null && !update.wednesday.isEmpty())
            timetable.wednesday = update.getWednesday();

        if (update.thursday != null && !update.thursday.isEmpty())
            timetable.thursday = update.getThursday();

        if (update.friday != null && !update.friday.isEmpty())
            timetable.friday = update.getFriday();

        if (update.saturday != null && !update.saturday.isEmpty())
            timetable.saturday = update.getSaturday();

        return timetable;
    }
}
