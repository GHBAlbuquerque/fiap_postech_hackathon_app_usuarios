//package com.fiap.hackathon.common.interfaces.usecase;
//
//import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
//import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
//import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
//import com.fiap.hackathon.common.exceptions.custom.UserDeactivationException;
//import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
//import com.fiap.hackathon.common.interfaces.gateways.UserGateway;
//import com.fiap.hackathon.core.entity.User;
//
//public interface UserUseCase {
//
//    User getUserByCpf(String cpf, UserGateway userGateway) throws EntityNotFoundException;
//
//    User getUserById(Long id, UserGateway userGateway) throws EntityNotFoundException;
//
//    User registerUser(User user, UserGateway userGateway, AuthenticationGateway authenticationGateway)
//            throws AlreadyRegisteredException, IdentityProviderException;
//
//    Boolean validateCpfInUse(String cpf, UserGateway userGateway);
//
//    Boolean confirmUserSignUp(String cpf, String code, AuthenticationGateway authenticationGateway) throws IdentityProviderException;
//
//    Boolean deactivateUser(Long id, String cpf, String password, UserGateway userGateway, AuthenticationGateway authenticationGateway) throws EntityNotFoundException, UserDeactivationException;
//}
