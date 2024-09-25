package hackathon.core.usecase;

import com.fiap.hackathon.common.exceptions.custom.*;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.gateways.TimetableGateway;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.DoctorTimetable;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import com.fiap.hackathon.core.usecase.DoctorUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorUseCaseImplTest {

    @Mock
    private DoctorGateway doctorGateway;

    @Mock
    private AuthenticationGateway authenticationGateway;

    @Mock
    private TimetableGateway timetableGateway;

    @InjectMocks
    private DoctorUseCaseImpl doctorUseCase;

    @Test
    void shouldRegisterDoctorSuccessfully() throws CreateEntityException, EntitySearchException, IdentityProviderException {
        // Arrange
        final var doctor = getDoctor();

        when(doctorGateway.getDoctorByEmail(doctor.getEmail())).thenReturn(null);
        when(doctorGateway.getDoctorByCpf(doctor.getCpf())).thenReturn(null);
        when(authenticationGateway.createUserAuthentication(doctor.getEmail(), doctor.getPassword(), doctor.getEmail())).thenReturn(true);
        when(doctorGateway.save(doctor)).thenReturn(doctor);

        // Act
        final var result = doctorUseCase.register(doctor, doctorGateway, authenticationGateway);

        // Assert
        assertEquals(doctor, result);
    }

    @Test
    void shouldRegisterDoctorTimetableSuccessfully() throws CreateEntityException, EntitySearchException {
        // Arrange
        final var timetable = getDoctorTimetable();

        when(timetableGateway.getTimetableByDoctorId(timetable.getDoctorId())).thenReturn(null);
        when(timetableGateway.save(timetable)).thenReturn(timetable);

        // Act
        final var result = doctorUseCase.registerDoctorTimetable(timetable, timetableGateway);

        // Assert
        assertEquals(timetable, result);
    }

    @Test
    void shouldFailWhenTimetableAlreadyExists() throws EntitySearchException {
        // Arrange
        final var timetable = getDoctorTimetable();


        when(timetableGateway.getTimetableByDoctorId(timetable.getDoctorId())).thenReturn(timetable);

        // Act & Assert
        assertThrows(CreateEntityException.class, () -> doctorUseCase.registerDoctorTimetable(timetable, timetableGateway));
    }

    @Test
    void shouldUpdateDoctorTimetableSuccessfully() throws EntitySearchException, CreateEntityException {
        // Arrange
        final var timetable = getDoctorTimetable();


        final var existingTimetable = getDoctorTimetable();

        when(timetableGateway.getTimetableByDoctorId(timetable.getDoctorId())).thenReturn(existingTimetable);
        when(timetableGateway.save(any(DoctorTimetable.class))).thenReturn(timetable);

        // Act
        final var result = doctorUseCase.updateDoctorTimetable(timetable, timetableGateway);

        // Assert
        assertEquals(timetable, result);
    }

    @Test
    void shouldFailWhenTimetableNotFound() {
        // Arrange
        final var timetable = getDoctorTimetable();

        // Act & Assert
        assertThrows(EntitySearchException.class, () -> doctorUseCase.updateDoctorTimetable(timetable, timetableGateway));
    }

    @Test
    void shouldReturnDoctorByIdSuccessfully() throws EntitySearchException {
        // Arrange
        final var doctorId = "doctor123";
        final var doctor = getDoctor();

        when(doctorGateway.getDoctorById(doctorId)).thenReturn(doctor);

        // Act
        final var result = doctorUseCase.getDoctorById(doctorId, doctorGateway);

        // Assert
        assertEquals(doctor, result);
    }

    @Test
    void shouldValidateInformationSuccessfully() throws EntitySearchException, AlreadyRegisteredException {
        // Arrange
        final var email = "test@doctor.com";
        final var cpf = "12345678901";

        when(doctorGateway.getDoctorByEmail(email)).thenReturn(null);
        when(doctorGateway.getDoctorByCpf(cpf)).thenReturn(null);

        // Act
        final var result = doctorUseCase.validateInformationInUse(email, cpf, doctorGateway);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldFailWhenEmailAlreadyInUse() throws EntitySearchException {
        // Arrange
        final var email = "test@doctor.com";
        final var cpf = "12345678901";

        final var doctor = new Doctor();
        when(doctorGateway.getDoctorByEmail(email)).thenReturn(doctor);

        // Act & Assert
        assertThrows(AlreadyRegisteredException.class, () -> doctorUseCase.validateInformationInUse(email, cpf, doctorGateway));
    }

    @Test
    void shouldFailWhenCpfAlreadyInUse() throws EntitySearchException {
        // Arrange
        final var email = "test@doctor.com";
        final var cpf = "12345678901";

        final var doctor = new Doctor();
        when(doctorGateway.getDoctorByEmail(email)).thenReturn(null);
        when(doctorGateway.getDoctorByCpf(cpf)).thenReturn(doctor);

        // Act & Assert
        assertThrows(AlreadyRegisteredException.class, () -> doctorUseCase.validateInformationInUse(email, cpf, doctorGateway));
    }

    @Test
    void shouldSearchDoctorsBySpecialtySuccessfully() throws EntitySearchException {
        // Arrange
        final var specialty = MedicalSpecialtyEnum.CARDIOLOGISTA;
        final var page = 0;
        final var size = 10;

        final var doctorList = List.of(new Doctor(), new Doctor());
        when(doctorGateway.getActiveDoctorsBySpecialty(specialty.name(), Boolean.TRUE)).thenReturn(doctorList);

        // Act
        final var result = doctorUseCase.searchDoctorsBySpecialty(specialty, page, size, doctorGateway);

        // Assert
        assertEquals(doctorList, result);
    }

    @Test
    void shouldNotFailWhenNoDoctorsFoundBySpecialty() throws EntitySearchException {
        // Arrange
        final var specialty = MedicalSpecialtyEnum.CARDIOLOGISTA;
        final var page = 0;
        final var size = 10;

        when(doctorGateway.getActiveDoctorsBySpecialty(specialty.name(), Boolean.TRUE)).thenReturn(Collections.emptyList());

        // Act
        final var result = doctorUseCase.searchDoctorsBySpecialty(specialty, page, size, doctorGateway);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowEntitySearchExceptionWhenErrorOccurs() throws EntitySearchException {
        // Arrange
        final var specialty = MedicalSpecialtyEnum.CARDIOLOGISTA;
        final var page = 0;
        final var size = 10;

        when(doctorGateway.getActiveDoctorsBySpecialty(specialty.name(), Boolean.TRUE)).thenThrow(new EntitySearchException(ExceptionCodes.USER_01_NOT_FOUND, "Error"));

        // Act & Assert
        assertThrows(EntitySearchException.class, () -> doctorUseCase.searchDoctorsBySpecialty(specialty, page, size, doctorGateway));
    }

    private static Doctor getDoctor() {
        final var doctor = new Doctor();
        doctor.setEmail("test@doctor.com");
        doctor.setCpf("12345678901");
        doctor.setPassword("password");
        return doctor;
    }

    private static DoctorTimetable getDoctorTimetable() {
        return new DoctorTimetable("id", "doctor123", Set.of(), Set.of("10:00-11:00"), Set.of("10:00-11:00"), Set.of("--:--"), Set.of("11:00-12:00"), Set.of("--:--"), Set.of("10:00-11:00"));
    }

}