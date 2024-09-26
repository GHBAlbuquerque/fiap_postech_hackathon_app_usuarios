package hackathon.core.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.PatientGateway;
import com.fiap.hackathon.core.entity.Patient;
import com.fiap.hackathon.core.usecase.PatientUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientUseCaseImplTest {

    @Mock
    private PatientGateway patientGateway;

    @Mock
    private AuthenticationGateway authenticationGateway;

    @InjectMocks
    private PatientUseCaseImpl patientUseCase;

    @Test
    void shouldRegisterPatientSuccessfully() throws AlreadyRegisteredException, IdentityProviderException, CreateEntityException, EntitySearchException {
        // Arrange
        final var patient = createPatient();
        when(patientGateway.getPatientByEmail(patient.getEmail())).thenReturn(null);
        when(patientGateway.getPatientByCpf(patient.getCpf())).thenReturn(null);
        when(authenticationGateway.createUserAuthentication(patient.getEmail(), patient.getPassword(), patient.getEmail())).thenReturn(true);
        when(patientGateway.save(patient)).thenReturn(patient);

        // Act
        final var result = patientUseCase.register(patient, patientGateway, authenticationGateway);

        // Assert
        assertEquals(patient, result);
    }

    @Test
    void shouldGetPatientByIdSuccessfully() throws EntitySearchException {
        // Arrange
        final var patientId = "123";
        final var patient = createPatient();
        when(patientGateway.getPatientById(patientId)).thenReturn(patient);

        // Act
        final var result = patientUseCase.getPatientById(patientId, patientGateway);

        // Assert
        assertEquals(patient, result);
    }

    @Test
    void shouldValidateInformationInUseSuccessfully() throws EntitySearchException, AlreadyRegisteredException {
        // Arrange
        final var patient = createPatient();
        when(patientGateway.getPatientByEmail(patient.getEmail())).thenReturn(null);
        when(patientGateway.getPatientByCpf(patient.getCpf())).thenReturn(null);

        // Act
        final var result = patientUseCase.validateInformationInUse(patient.getEmail(), patient.getCpf(), patientGateway);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldFailWhenEmailIsAlreadyInUse() throws EntitySearchException {
        // Arrange
        final var patient = createPatient();
        when(patientGateway.getPatientByEmail(patient.getEmail())).thenReturn(patient);

        // Act & Assert
        assertThrows(AlreadyRegisteredException.class, () ->
                patientUseCase.validateInformationInUse(patient.getEmail(), patient.getCpf(), patientGateway));
    }

    @Test
    void shouldFailWhenCpfIsAlreadyInUse() throws EntitySearchException {
        // Arrange
        final var patient = createPatient();
        when(patientGateway.getPatientByEmail(patient.getEmail())).thenReturn(null);
        when(patientGateway.getPatientByCpf(patient.getCpf())).thenReturn(patient);

        // Act & Assert
        assertThrows(AlreadyRegisteredException.class, () ->
                patientUseCase.validateInformationInUse(patient.getEmail(), patient.getCpf(), patientGateway));
    }

    // Método privado para gerar um Patient
    private Patient createPatient() {
        final var patient = new Patient();
        patient.setEmail("patient@test.com");
        patient.setCpf("12345678901");
        patient.setPassword("password123");
        return patient;
    }
}