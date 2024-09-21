package com.fiap.hackathon.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class GetDoctorTimetableResponse {

    private List<String> sunday;
    private List<String> monday;
    private List<String> tuesday;
    private List<String> wednesday;
    private List<String> thursday;
    private List<String> friday;
    private List<String> saturday;
}
