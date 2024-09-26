package hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.communication.gateways.AuthenticationGatewayImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ConfirmSignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationGatewayImplTest {

    @Mock
    private CognitoIdentityProviderClient identityProviderClientMock;

    @InjectMocks
    private AuthenticationGatewayImpl authenticationGatewayMock;

    @Test
    void createUserAuthenticationTest() {
        when(identityProviderClientMock.signUp(any(SignUpRequest.class)))
                .thenReturn(null);

        Assertions.assertDoesNotThrow(
                () -> authenticationGatewayMock.createUserAuthentication("username", "password", "email")
        );
    }

    @Test
    void createUserAuthenticationErrorTest() {
        when(identityProviderClientMock.signUp(any(SignUpRequest.class)))
                .thenThrow(CognitoIdentityProviderException.class);

        Assertions.assertThrows(
                IdentityProviderException.class,
                () -> authenticationGatewayMock.createUserAuthentication("username", "password", "email")
        );
    }

    @Test
    void confirmSignUpTest() {
        when(identityProviderClientMock.confirmSignUp(any(ConfirmSignUpRequest.class)))
                .thenReturn(null);

        Assertions.assertDoesNotThrow(
                () -> authenticationGatewayMock.confirmSignUp("username", "code")
        );
    }

    @Test
    void confirmSignUpErrorTest() {
        when(identityProviderClientMock.confirmSignUp(any(ConfirmSignUpRequest.class)))
                .thenThrow(CognitoIdentityProviderException.class);

        Assertions.assertThrows(
                IdentityProviderException.class,
                () -> authenticationGatewayMock.confirmSignUp("username", "code")
        );
    }
}