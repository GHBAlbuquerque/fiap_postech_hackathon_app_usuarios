package com.fiap.hackathon.common.beans;

import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.gateways.PacientGateway;
import com.fiap.hackathon.communication.gateways.AuthenticationGatewayImpl;
import com.fiap.hackathon.communication.gateways.DoctorGatewayImpl;
import com.fiap.hackathon.communication.gateways.PacientGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class GatewayBeanDeclaration {

    @Bean
    public DoctorGateway doctorGateway(DynamoDbClient dynamoDbClient) {
        return new DoctorGatewayImpl(dynamoDbClient);
    }

    @Bean
    public PacientGateway pacientGateway(DynamoDbClient dynamoDbClient) {
        return new PacientGatewayImpl(dynamoDbClient);
    }

    @Bean
    public AuthenticationGateway authenticationGateway(CognitoIdentityProviderClient cognitoIdentityProviderClient) {
        return new AuthenticationGatewayImpl(cognitoIdentityProviderClient);
    }
}
