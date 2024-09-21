package com.fiap.hackathon.common.builders;

import com.fiap.hackathon.common.dto.request.RegisterDoctorTimetableRequest;
import com.fiap.hackathon.common.dto.response.GetDoctorTimetableResponse;
import com.fiap.hackathon.core.entity.DoctorTimetable;

public class DoctorTimetableBuilder {

    public static DoctorTimetable fromRequestToDomain(String doctorId, RegisterDoctorTimetableRequest request) {
        return new DoctorTimetable()
                .setDoctorId(doctorId)
                .setSunday(request.getSunday())
                .setMonday(request.getMonday())
                .setTuesday(request.getTuesday())
                .setWednesday(request.getWednesday())
                .setThursday(request.getThursday())
                .setFriday(request.getFriday())
                .setSaturday(request.getSaturday());
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
