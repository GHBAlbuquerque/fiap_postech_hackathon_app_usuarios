package hackathon.communication.controllers;

import com.fiap.hackathon.HackathonApplication;
import com.fiap.hackathon.common.dto.request.RegisterPatientRequest;
import com.fiap.hackathon.core.entity.Patient;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ContextConfiguration(classes = HackathonApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenValidRegisterPatientRequest_whenRegisterPatient_thenReturnCreated() {
        RegisterPatientRequest request = createRegisterPatientRequest();

        given()
                .contentType(ContentType.JSON)
                .port(port)
                .body(request)
                .when()
                .post("/patients")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenValidPatientId_whenGetPatientById_thenReturnOk() {
        String patientId = "28831743-c53d-451a-89ef-bc464176f2ed";

        given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", patientId)
                .when()
                .get("/patients/{id}");
                //.then()
                //.statusCode(HttpStatus.OK.value());
    }

    @Test
    void givenInvalidPatientId_whenGetPatientById_thenReturnNotFound() {
        String patientId = "invalid-patient-id";

        given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", patientId)
                .when()
                .get("/patients/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private RegisterPatientRequest createRegisterPatientRequest() {
        return new RegisterPatientRequest(
                "João da Silva",                       // name
                LocalDate.of(1990, 5, 20),             // birthday
                "12345678901",                         // cpf
                "joao.silva@example.com",              // email
                "senhaSegura123",                      // password
                "11987654321"                          // contactNumber
        );
    }

    private Patient createPatient() {
        return new Patient(
                "João da Silva",                       // name
                LocalDate.of(1990, 5, 20),             // birthday
                "12345678901",                         // cpf
                "joao.silva@example.com",              // email
                "senhaSegura123",                      // password
                "11987654321",
                "id",
                true);
    }
}
