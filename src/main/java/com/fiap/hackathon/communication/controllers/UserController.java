//package com.fiap.hackathon.communication.controllers;
//
//import com.amazonaws.services.quicksight.model.RegisterUserRequest;
//import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
//import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
//import com.fiap.hackathon.common.exceptions.model.ExceptionDetails;
//import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
//import com.fiap.hackathon.common.interfaces.gateways.UserGateway;
//import com.fiap.hackathon.common.interfaces.usecase.UserUseCase;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    private final AuthenticationGateway authenticationGateway;
//    private final UserGateway userGateway;
//    private final UserUseCase useCase;
//
//    public UserController(AuthenticationGateway authenticationGateway, UserGateway userGateway, UserUseCase userUseCase) {
//        this.authenticationGateway = authenticationGateway;
//        this.userGateway = userGateway;
//        this.useCase = userUseCase;
//    }
//
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
//    })
//    @PostMapping(produces = "application/json", consumes = "application/json")
//    public ResponseEntity<RegisterUserResponse> registerUser(
//            @RequestBody @Valid RegisterUserRequest request
//    ) throws AlreadyRegisteredException, IdentityProviderException {
//
//        final var userReq = UserBuilder.fromRequestToDomain(request);
//        final var user = useCase.registerUser(userReq, userGateway, authenticationGateway);
//        final var userId = user.getId().toString();
//
//        final var uri = URI.create(userId);
//
//        return ResponseEntity.created(uri).body(new RegisterUserResponse(userId));
//
//    }
//
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
//    })
//    @GetMapping(produces = "application/json", consumes = "application/json")
//    public ResponseEntity<GetUserResponse> getUserByCpf(@RequestParam(required = true) String cpf)
//            throws EntityNotFoundException {
//
//        final var user = useCase.getUserByCpf(cpf, userGateway);
//        final var userResponse = UserBuilder.fromDomainToResponse(user);
//
//        return ResponseEntity.ok(userResponse);
//    }
//
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
//    })
//    @GetMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
//    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id)
//            throws EntityNotFoundException {
//
//        final var user = useCase.getUserById(id, userGateway);
//        final var userResponse = UserBuilder.fromDomainToResponse(user);
//
//        return ResponseEntity.ok(userResponse);
//    }
//
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
//    })
//    @PostMapping(value = "/confirmation", produces = "application/json", consumes = "application/json")
//    public ResponseEntity<Boolean> confirmSignUp(@RequestBody(required = true) @Valid ConfirmSignUpRequest confirmSignUpRequest)
//            throws IdentityProviderException {
//
//        final var response = useCase.confirmUserSignUp(confirmSignUpRequest.getCpf(),
//                confirmSignUpRequest.getCode(),
//                authenticationGateway);
//
//        return ResponseEntity.ok(response);
//    }
//
//
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
//    })
//    @DeleteMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
//    public ResponseEntity<?> deactivateUser(@PathVariable Long id,
//                                                @RequestHeader("cpf_cliente") String cpf,
//                                                @RequestHeader("senha_cliente") String password)
//            throws EntityNotFoundException, UserDeactivationException {
//
//        useCase.deactivateUser(id, cpf, password, userGateway, authenticationGateway);
//
//        return ResponseEntity.ok().build();
//    }
//}
