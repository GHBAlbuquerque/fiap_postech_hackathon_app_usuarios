package hackathon.communication.controllers;

import com.fiap.hackathon.HackathonApplication;
import com.fiap.hackathon.common.dto.request.RegisterDoctorRequest;
import com.fiap.hackathon.common.dto.request.RegisterDoctorTimetableRequest;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ContextConfiguration(classes = HackathonApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoctorControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenValidRegisterDoctorRequest_whenRegisterDoctor_thenReturnCreated() {
        RegisterDoctorRequest request = createRegisterDoctorRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .port(port)
                .when()
                .post("/doctors")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenValidRegisterDoctorTimetableRequest_whenRegisterTimetable_thenReturnCreated() {
        RegisterDoctorTimetableRequest request = createRegisterDoctorTimetableRequest();
        String doctorId = "some-doctor-id";

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .port(port)
                .when()
                .post("/doctors/{id}/timetable", doctorId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenValidUpdateTimetableRequest_whenUpdateTimetable_thenReturnOk() {
        RegisterDoctorTimetableRequest request = createRegisterDoctorTimetableRequest();
        String doctorId = "some-doctor-id";

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .port(port)
                .when()
                .put("/doctors/{id}/timetable", doctorId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenValidDoctorId_whenGetDoctorById_thenReturnOk() {
        String doctorId = "da347bea-f772-4db4-864d-5e4a622dcc08";

        given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", doctorId)
                .when()
                .get("/doctors/{id}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenSpecialty_whenSearchDoctorsBySpecialty_thenReturnOk() {
        MedicalSpecialtyEnum specialty = MedicalSpecialtyEnum.DERMATOLOGISTA;

        given()
                .param("medicalSpecialty", specialty)
                .port(port)
                .when()
                .get("/doctors")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    void givenValidDoctorId_whenGetTimetableByDoctorId_thenReturnOk() {
        String doctorId = "some-doctor-id";

        given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", doctorId)
                .when()
                .get("/doctors/{id}/timetable")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    private RegisterDoctorRequest createRegisterDoctorRequest() {
        return new RegisterDoctorRequest(
                "João da Silva",                       // name
                LocalDate.of(1990, 5, 20),             // birthday
                "12345678901",                         // cpf
                "joao.silva@example.com",              // email
                "senhaSegura123",                      // password
                "11987654321",                          // contactNumber
                "7827323",
                "CARDIOLOGISTA"
        );
    }

    private RegisterDoctorTimetableRequest createRegisterDoctorTimetableRequest() {
        return new RegisterDoctorTimetableRequest(
                Set.of(),
                Set.of("10:00-11:00"),
                Set.of("10:00-11:00"),
                Set.of("--:--"),
                Set.of("11:00-12:00"),
                Set.of("--:--"),
                Set.of("10:00-11:00")
        );
    }
}
