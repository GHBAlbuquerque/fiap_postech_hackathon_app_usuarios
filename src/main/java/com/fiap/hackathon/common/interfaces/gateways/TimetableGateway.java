package com.fiap.hackathon.common.interfaces.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.core.entity.DoctorTimetable;

import javax.print.Doc;

public interface TimetableGateway {

    DoctorTimetable save(DoctorTimetable doctorTimetable) throws CreateEntityException;

    DoctorTimetable getTimetableByDoctorId(String doctorId) throws EntitySearchException;
}
