package com.fiap.hackathon.communication.controllers;

import com.fiap.hackathon.common.builders.PatientBuilder;
import com.fiap.hackathon.common.dto.request.RegisterPatientRequest;
import com.fiap.hackathon.common.dto.response.GetPatientResponse;
import com.fiap.hackathon.common.dto.response.RegisterUserResponse;
import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.exceptions.model.ExceptionDetails;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.PatientGateway;
import com.fiap.hackathon.common.interfaces.usecase.PatientUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final AuthenticationGateway authenticationGateway;
    private final PatientGateway userGateway;
    private final PatientUseCase useCase;

    public PatientController(AuthenticationGateway authenticationGateway, PatientGateway userGateway, PatientUseCase useCase) {
        this.authenticationGateway = authenticationGateway;
        this.userGateway = userGateway;
        this.useCase = useCase;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<RegisterUserResponse> registerPatient(
            @RequestBody @Valid RegisterPatientRequest request
    ) throws AlreadyRegisteredException, IdentityProviderException, CreateEntityException {

        final var userReq = PatientBuilder.fromRequestToDomain(request);
        final var user = useCase.register(userReq, userGateway, authenticationGateway);
        final var userId = user.getId().toString();

        final var uri = URI.create(userId);

        return ResponseEntity.created(uri).body(new RegisterUserResponse(userId));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GetPatientResponse> getPatientById(@PathVariable String id)
            throws EntitySearchException {

        final var user = useCase.getPatientById(id, userGateway);
        final var userResponse = PatientBuilder.fromDomainToResponse(user);

        return ResponseEntity.ok(userResponse);
    }
}
