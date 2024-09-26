package hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.interfaces.repositories.DoctorRepository;
import com.fiap.hackathon.communication.gateways.DoctorGatewayImpl;
import com.fiap.hackathon.core.entity.Doctor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorGatewayImplTest {

    @Mock
    private DoctorRepository repository;

    @InjectMocks
    private DoctorGatewayImpl doctorGateway;

    @Test
    void shouldSaveDoctorSuccessfully() throws CreateEntityException {
        // Arrange
        final var doctor = createDoctor();
        when(repository.save(doctor)).thenReturn(doctor);

        // Act
        final var result = doctorGateway.save(doctor);

        // Assert
        assertEquals(doctor, result);
    }

    @Test
    void shouldGetDoctorByIdSuccessfully() throws EntitySearchException {
        // Arrange
        final var doctorId = "1";
        final var doctor = createDoctor();
        when(repository.getDoctorById(doctorId)).thenReturn(doctor);

        // Act
        final var result = doctorGateway.getDoctorById(doctorId);

        // Assert
        assertEquals(doctor, result);
    }

    @Test
    void shouldGetDoctorByCpfSuccessfully() throws EntitySearchException {
        // Arrange
        final var cpf = "12345678900";
        final var doctor = createDoctor();
        when(repository.getDoctorByCpf(cpf)).thenReturn(doctor);

        // Act
        final var result = doctorGateway.getDoctorByCpf(cpf);

        // Assert
        assertEquals(doctor, result);
    }

    @Test
    void shouldGetDoctorByEmailSuccessfully() throws EntitySearchException {
        // Arrange
        final var email = "doctor@example.com";
        final var doctor = createDoctor();
        when(repository.getDoctorByEmail(email)).thenReturn(doctor);

        // Act
        final var result = doctorGateway.getDoctorByEmail(email);

        // Assert
        assertEquals(doctor, result);
    }

    @Test
    void shouldGetActiveDoctorsBySpecialtySuccessfully() throws EntitySearchException {
        // Arrange
        final var specialty = "CARDIOLOGY";
        final var doctors = List.of(createDoctor());
        when(repository.getActiveDoctorsBySpecialty(specialty, true)).thenReturn(doctors);

        // Act
        final var result = doctorGateway.getActiveDoctorsBySpecialty(specialty, true);

        // Assert
        assertEquals(doctors, result);
    }

    private Doctor createDoctor() {
        final var doctor = new Doctor();
        doctor.setId("1");
        doctor.setEmail("doctor@example.com");
        doctor.setCpf("12345678900");
        doctor.setIsActive(true);
        return doctor;
    }

}
