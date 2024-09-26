package com.fiap.hackathon.common.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
