package com.fiap.hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.interfaces.gateways.TimetableGateway;
import com.fiap.hackathon.common.interfaces.repositories.TimetableRepository;
import com.fiap.hackathon.core.entity.DoctorTimetable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimetableGatewayImpl implements TimetableGateway {

    private final TimetableRepository repository;

    public TimetableGatewayImpl(TimetableRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LogManager.getLogger(TimetableGatewayImpl.class);

    @Override
    public DoctorTimetable save(DoctorTimetable doctorTimetable) throws CreateEntityException {
        return repository.save(doctorTimetable);
    }

    @Override
    public DoctorTimetable getTimetableByDoctorId(String doctorId) throws EntitySearchException {
        return repository.getTimetableByDoctorId(doctorId);
    }
}
