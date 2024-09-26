package com.fiap.hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.logging.LoggingPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ConfirmSignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;

import java.util.ArrayList;

import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_03_IDENTITY_PROVIDER;

@Component
public class AuthenticationGatewayImpl implements AuthenticationGateway {

    @Value("${aws.cognito.clientid}")
    private String identityProviderClientId;

    @Value("${aws.cognito.clientSecretKey}")
    private String identityProviderClientSecretKey;

    private final CognitoIdentityProviderClient identityProviderClient;

    private static final Logger logger = LogManager.getLogger(AuthenticationGatewayImpl.class);

    public AuthenticationGatewayImpl(CognitoIdentityProviderClient identityProviderClient) {
        this.identityProviderClient = identityProviderClient;
    }


    @Override
    public Boolean createUserAuthentication(String username,
                                            String password,
                                            String email) throws IdentityProviderException {

        String emailField = "email";
        var attributeType = AttributeType.builder()
                .name(emailField)
                .value(email)
                .build();

        var attrs = new ArrayList<AttributeType>();
        attrs.add(attributeType);

        try {

            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .userAttributes(attrs)
                    .username(username)
                    .clientId(identityProviderClientId)
                    .password(password)
                    .build();

            identityProviderClient.signUp(signUpRequest);

            logger.info(LoggingPattern.IDENTITY_PROVIDER_USER_CREATED,
                    username);


            return true;

        } catch (CognitoIdentityProviderException ex) {

            logger.error("Error using Identity Provider. Message: {}", ex.getMessage());

            throw new IdentityProviderException(USER_03_IDENTITY_PROVIDER, ex.getMessage());
        }
    }

    public Boolean confirmSignUp(String username, String code) throws IdentityProviderException {
        try {
            final var signUpRequest = ConfirmSignUpRequest.builder()
                    .clientId(identityProviderClientId)
                    .confirmationCode(code)
                    .username(username)
                    .build();

            identityProviderClient.confirmSignUp(signUpRequest);

            logger.info(LoggingPattern.IDENTITY_PROVIDER_USER_CONFIRMED,
                    username);

            return true;

        } catch (CognitoIdentityProviderException ex) {

            logger.error("Error using Identity Provider. Message: {}", ex.getMessage());

            throw new IdentityProviderException(USER_03_IDENTITY_PROVIDER, ex.getMessage());
        }
    }

}
