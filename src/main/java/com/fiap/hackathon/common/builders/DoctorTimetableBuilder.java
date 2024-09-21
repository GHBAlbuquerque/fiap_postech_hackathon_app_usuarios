package com.fiap.hackathon.common.builders;

import com.fiap.hackathon.common.dto.request.RegisterDoctorTimetableRequest;
import com.fiap.hackathon.common.dto.response.GetDoctorTimetableResponse;
import com.fiap.hackathon.core.entity.DoctorTimetable;

public class DoctorTimetableBuilder {

    public static DoctorTimetable fromRequestToDomain(RegisterDoctorTimetableRequest request) {
        return new DoctorTimetable();
    }

    public static GetDoctorTimetableResponse fromDomainToResponse(DoctorTimetable doctorTimetable) {
        return new GetDoctorTimetableResponse()
                .setSunday(doctorTimetable.getSunday())
                .setMonday(doctorTimetable.getMonday())
                .setTuesday(doctorTimetable.getTuesday())
                .setWednesday(doctorTimetable.getWednesday())
                .setThursday(doctorTimetable.getThursday())
                .setFriday(doctorTimetable.getFriday())
                .setSaturday(doctorTimetable.getSaturday());
    }
}
