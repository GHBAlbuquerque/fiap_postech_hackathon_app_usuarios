package hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.exceptions.custom.ExceptionCodes;
import com.fiap.hackathon.common.interfaces.repositories.PatientRepository;
import com.fiap.hackathon.communication.gateways.PatientGatewayImpl;
import com.fiap.hackathon.core.entity.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientGatewayImplTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientGatewayImpl patientGateway;

    @Test
    void shouldSavePatientSuccessfully() throws CreateEntityException {
        // Arrange
        final var patient = createPatient();
        when(repository.save(patient)).thenReturn(patient);

        // Act
        final var result = patientGateway.save(patient);

        // Assert
        assertEquals(patient, result);
    }

    @Test
    void shouldThrowCreateEntityExceptionWhenSaveFails() throws CreateEntityException {
        // Arrange
        final var patient = createPatient();
        when(repository.save(patient)).thenThrow(new CreateEntityException(ExceptionCodes.USER_08_USER_CREATION, "Error"));

        // Act & Assert
        assertThrows(CreateEntityException.class, () -> patientGateway.save(patient));
    }

    @Test
    void shouldGetPatientByIdSuccessfully() throws EntitySearchException {
        // Arrange
        final var patientId = "1";
        final var patient = createPatient();
        when(repository.getPatientById(patientId)).thenReturn(patient);

        // Act
        final var result = patientGateway.getPatientById(patientId);

        // Assert
        assertEquals(patient, result);
    }

    @Test
    void shouldGetPatientByCpfSuccessfully() throws EntitySearchException {
        // Arrange
        final var cpf = "12345678900";
        final var patient = createPatient();
        when(repository.getPatientByCpf(cpf)).thenReturn(patient);

        // Act
        final var result = patientGateway.getPatientByCpf(cpf);

        // Assert
        assertEquals(patient, result);
    }

    @Test
    void shouldGetPatientByEmailSuccessfully() throws EntitySearchException {
        // Arrange
        final var email = "patient@example.com";
        final var patient = createPatient();
        when(repository.getPatientByEmail(email)).thenReturn(patient);

        // Act
        final var result = patientGateway.getPatientByEmail(email);

        // Assert
        assertEquals(patient, result);
    }


    private Patient createPatient() {
        final var patient = new Patient();
        patient.setId("1");
        patient.setEmail("patient@example.com");
        patient.setCpf("12345678900");
        patient.setIsActive(true);
        return patient;
    }
}