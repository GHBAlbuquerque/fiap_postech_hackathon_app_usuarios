package com.fiap.hackathon.common.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Set;

@Getter
public class RegisterDoctorTimetableRequest {

    @NotNull
    private Set<String> sunday;

    @NotNull
    private Set<String> monday;

    @NotNull
    private Set<String> tuesday;

    @NotNull
    private Set<String> wednesday;

    @NotEmpty
    private Set<String> thursday;

    @NotNull
    private Set<String> friday;

    @NotNull
    private Set<String> saturday;
}
