package hackathon.communication.controllers;

import com.fiap.hackathon.HackathonApplication;
import com.fiap.hackathon.common.dto.request.ConfirmSignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import java.security.SecureRandom;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@ContextConfiguration(classes = HackathonApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationControllerTest {

    @LocalServerPort
    private int port;

    private static final String EMAIL_TEST = "email_" + new SecureRandom() + "@email.com";

    @Test
    void givenConfirmSignUpRequestThenRespondWithSuccess() {
        final var confirmSignUpRequest = new ConfirmSignUpRequest(EMAIL_TEST, "code");

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(confirmSignUpRequest)
                .when()
                .post("/authenticate")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(JSON);
    }
}
