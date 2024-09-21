package com.fiap.hackathon.common.interfaces.repositories;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.core.entity.DoctorTimetable;

public interface TimetableRepository {

    DoctorTimetable save(DoctorTimetable doctorTimetable) throws CreateEntityException;

    DoctorTimetable getTimetableByDoctorId(String doctorId) throws EntitySearchException;

}
