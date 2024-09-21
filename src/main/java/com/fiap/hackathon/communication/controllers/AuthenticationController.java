package com.fiap.hackathon.communication.controllers;

import com.fiap.hackathon.common.dto.request.ConfirmSignUpRequest;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.exceptions.model.ExceptionDetails;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    private final AuthenticationGateway authenticationGateway;

    public AuthenticationController(AuthenticationGateway authenticationGateway) {
        this.authenticationGateway = authenticationGateway;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(value = "/confirmation", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> confirmSignUp(@RequestBody(required = true) @Valid ConfirmSignUpRequest confirmSignUpRequest)
            throws IdentityProviderException {

        final var response = authenticationGateway.confirmSignUp(confirmSignUpRequest.getEmail(), confirmSignUpRequest.getCode());

        return ResponseEntity.ok(response);
    }
}
