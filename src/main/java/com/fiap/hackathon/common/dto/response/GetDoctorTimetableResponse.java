package com.fiap.hackathon.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class GetDoctorTimetableResponse {

    private Set<String> sunday;
    private Set<String> monday;
    private Set<String> tuesday;
    private Set<String> wednesday;
    private Set<String> thursday;
    private Set<String> friday;
    private Set<String> saturday;
}
