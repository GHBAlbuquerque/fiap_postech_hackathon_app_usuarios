package com.fiap.hackathon.communication.controllers;


import com.fiap.hackathon.common.builders.DoctorBuilder;
import com.fiap.hackathon.common.dto.request.RegisterDoctorRequest;
import com.fiap.hackathon.common.dto.response.GetDoctorResponse;
import com.fiap.hackathon.common.dto.response.RegisterUserResponse;
import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.exceptions.model.ExceptionDetails;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.usecase.DoctorUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final AuthenticationGateway authenticationGateway;
    private final DoctorGateway userGateway;
    private final DoctorUseCase useCase;

    public DoctorController(AuthenticationGateway authenticationGateway, DoctorGateway userGateway, DoctorUseCase useCase) {
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
    public ResponseEntity<RegisterUserResponse> registerDoctor(
            @RequestBody @Valid RegisterDoctorRequest request
    ) throws AlreadyRegisteredException, IdentityProviderException {

        final var userReq = DoctorBuilder.fromRequestToDomain(request);
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
    public ResponseEntity<GetDoctorResponse> getDoctorById(@PathVariable Long id)
            throws EntityNotFoundException {

        final var user = useCase.getDoctorById(id, userGateway);
        final var userResponse = DoctorBuilder.fromDomainToResponse(user);

        return ResponseEntity.ok(userResponse);
    }


}
