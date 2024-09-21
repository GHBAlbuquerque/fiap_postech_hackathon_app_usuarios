package com.fiap.hackathon.communication.controllers;


import com.fiap.hackathon.common.builders.DoctorBuilder;
import com.fiap.hackathon.common.builders.DoctorTimetableBuilder;
import com.fiap.hackathon.common.dto.request.RegisterDoctorRequest;
import com.fiap.hackathon.common.dto.request.RegisterDoctorTimetableRequest;
import com.fiap.hackathon.common.dto.response.GetDoctorResponse;
import com.fiap.hackathon.common.dto.response.GetDoctorTimetableResponse;
import com.fiap.hackathon.common.dto.response.RegisterUserResponse;
import com.fiap.hackathon.common.dto.response.SearchDoctorResponse;
import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.exceptions.model.ExceptionDetails;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.gateways.TimetableGateway;
import com.fiap.hackathon.common.interfaces.usecase.DoctorUseCase;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final AuthenticationGateway authenticationGateway;
    private final DoctorGateway gateway;
    private final TimetableGateway timetableGateway;
    private final DoctorUseCase useCase;

    public DoctorController(AuthenticationGateway authenticationGateway, DoctorGateway userGateway, TimetableGateway timetableGateway, DoctorUseCase useCase) {
        this.authenticationGateway = authenticationGateway;
        this.gateway = userGateway;
        this.timetableGateway = timetableGateway;
        this.useCase = useCase;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<RegisterUserResponse> registerDoctor(
            @RequestBody @Valid RegisterDoctorRequest request
    ) throws AlreadyRegisteredException, IdentityProviderException, EntitySearchException, CreateEntityException {

        final var userReq = DoctorBuilder.fromRequestToDomain(request);
        final var user = useCase.register(userReq, gateway, authenticationGateway);
        final var userId = user.getId().toString();

        final var uri = URI.create(userId);

        return ResponseEntity.created(uri).body(new RegisterUserResponse(userId));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(value = "/{id}/timetable", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GetDoctorTimetableResponse> registerTimetable(
            @PathVariable String id,
            @RequestBody @Valid RegisterDoctorTimetableRequest request
    ) throws CreateEntityException {

        final var timetable = DoctorTimetableBuilder.fromRequestToDomain(id, request);
        final var result = useCase.registerDoctorTimetable(timetable, timetableGateway);
        final var timetableId = result.getId();

        final var uri = URI.create(timetableId);

        return ResponseEntity
                .created(uri)
                .body(DoctorTimetableBuilder.fromDomainToResponse(result));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GetDoctorResponse> getDoctorById(@PathVariable String id)
            throws EntitySearchException {

        final var user = useCase.getDoctorById(id, gateway);
        final var userResponse = DoctorBuilder.fromDomainToResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @GetMapping(produces = "application/json"/*, consumes = "application/json"*/)
    public ResponseEntity<List<SearchDoctorResponse>> searchDoctorsBySpecialty(
            @RequestParam(required = false) MedicalSpecialtyEnum medicalSpecialty,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) throws EntitySearchException {
        final var result = useCase.searchDoctorsBySpecialty(medicalSpecialty, page, size, gateway);

        return ResponseEntity.ok(
                result.stream().map(
                        DoctorBuilder::fromDomainToSearchResponse
                ).collect(Collectors.toList())
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @GetMapping(value = "/{id}/timetable", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GetDoctorResponse> getTimetableByDoctorId(@PathVariable String id)
            throws EntitySearchException {

        final var user = useCase.getDoctorById(id, gateway);
        final var userResponse = DoctorBuilder.fromDomainToResponse(user);

        return ResponseEntity.ok(userResponse);
    }


}
