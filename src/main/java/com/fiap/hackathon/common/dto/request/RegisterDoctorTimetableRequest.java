package com.fiap.hackathon.common.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class RegisterDoctorTimetableRequest {

    @NotNull
    private List<String> sunday;

    @NotNull
    private List<String> monday;

    @NotNull
    private List<String> tuesday;

    @NotNull
    private List<String> wednesday;

    @NotEmpty
    private List<String> thursday;

    @NotNull
    private List<String> friday;

    @NotNull
    private List<String> saturday;
}
