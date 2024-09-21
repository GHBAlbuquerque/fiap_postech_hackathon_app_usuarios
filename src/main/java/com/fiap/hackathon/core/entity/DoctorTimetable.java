package com.fiap.hackathon.core.entity;

import com.fiap.hackathon.common.exceptions.custom.ExceptionCodes;
import com.fiap.hackathon.common.exceptions.custom.TimetableSlotsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTimetable {

    private String id;
    private String doctorId;
    private List<String> sunday;
    private List<String> monday;
    private List<String> tuesday;
    private List<String> wednesday;
    private List<String> thursday;
    private List<String> friday;
    private List<String> saturday;

    public Boolean isValid() throws TimetableSlotsException {
        if (!sunday.isEmpty()) {
            throw new TimetableSlotsException(
                    ExceptionCodes.USER_11_UNAVAILABLE_TIMESLOT,
                    "Sunday timeslots are not allowed yet. Please request again leaving this day slots empty."
            );
        }

        checkTimeSlots(monday);
        checkTimeSlots(tuesday);
        checkTimeSlots(wednesday);
        checkTimeSlots(thursday);
        checkTimeSlots(friday);
        checkTimeSlots(saturday);

        return Boolean.TRUE;
    }

    private void checkTimeSlots(List<String> timeslots) throws TimetableSlotsException {
        for (String timeSlot : timeslots) {
            if (!TimeSlotsEnum.isValid(timeSlot)) {
                final var message = String.format("TimeSlot %s is not available. Please select times ranging from 7:00-18:00, with 1h duration max.", timeSlot);

                throw new TimetableSlotsException(
                        ExceptionCodes.USER_11_UNAVAILABLE_TIMESLOT,
                        message
                );
            }
        }
    }
}
