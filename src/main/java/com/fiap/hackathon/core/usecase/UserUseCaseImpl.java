//package com.fiap.hackathon.core.usecase;
//
//import com.fiap.fastfood.common.exceptions.custom.*;
//import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
//import com.fiap.fastfood.common.interfaces.gateways.UserGateway;
//import com.fiap.fastfood.common.interfaces.usecase.UserUseCase;
//import com.fiap.fastfood.core.entity.User;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.time.LocalDateTime;
//
//public class UserUseCaseImpl implements UserUseCase {
//
//    private static final Logger logger = LogManager.getLogger(UserUseCaseImpl.class);
//
//    @Override
//    public User registerUser(User user,
//                                     UserGateway userGateway,
//                                     AuthenticationGateway authenticationGateway)
//            throws AlreadyRegisteredException, IdentityProviderException {
//
//        final var cpfInUse = validateCpfInUse(user.getCpf(), userGateway);
//        final var validationResult = User.validate(user, cpfInUse);
//
//        if (Boolean.FALSE.equals(validationResult.getIsValid())) {
//            throw new AlreadyRegisteredException(
//                    ExceptionCodes.USER_02_ALREADY_REGISTERED,
//                    "Couldn't complete registration for user.",
//                    validationResult.getErrors()
//            );
//        }
//
//        authenticationGateway.createUserAuthentication(user.getCpf(),
//                user.getPassword(),
//                user.getEmail());
//
//        user.setIsActive(Boolean.TRUE);
//        return userGateway.saveUser(user);
//    }
//
//    @Override
//    public User getUserByCpf(String cpf, UserGateway userGateway)
//            throws EntityNotFoundException {
//        final var user = userGateway.getUserByCpf(cpf);
//
//        if (user == null) {
//            throw new EntityNotFoundException(
//                    ExceptionCodes.USER_01_NOT_FOUND,
//                    "User not found."
//            );
//        }
//
//        return user;
//    }
//
//    @Override
//    public User getUserById(Long id, UserGateway userGateway)
//            throws EntityNotFoundException {
//        final var user = userGateway.getUserById(id);
//
//        if (user == null) {
//            throw new EntityNotFoundException(
//                    ExceptionCodes.USER_01_NOT_FOUND,
//                    "Client not found."
//            );
//        }
//
//        return user;
//    }
//
//    @Override
//    public Boolean validateCpfInUse(String cpf, UserGateway userGateway) {
//        final var userUsingCpf = userGateway.getUserByCpf(cpf);
//
//        return userUsingCpf != null;
//    }
//
//    @Override
//    public Boolean confirmUserSignUp(String cpf, String code, AuthenticationGateway authenticationGateway)
//            throws IdentityProviderException {
//        return authenticationGateway.confirmSignUp(cpf, code);
//    }
//
//    @Override
//    public Boolean deactivateUser(Long id,
//                                      String cpf,
//                                      String password,
//                                      UserGateway userGateway,
//                                      AuthenticationGateway authenticationGateway) throws UserDeactivationException {
//        try {
//            logger.info("Iniciating user deactivation...");
//
//            final var user = getUserById(id, userGateway);
//
//            validateRequestingUser(user, cpf, password);
//
//            logger.info("Deactivating Identity Provider access.");
//
//            authenticationGateway.deleteUser(user.getCpf(), user.getPassword());
//
//            logger.info("Name, Contact Number and CPF will be forever erased.");
//
//            user.setIsActive(Boolean.FALSE);
//            user.setName(null);
//            user.setContactNumber(null);
//            user.setBirthday(null);
//            user.setCpf(null);
//            user.setUpdateTimestamp(LocalDateTime.now());
//
//            userGateway.saveUser(user);
//
//            logger.info("User successfully deactivated; PII removed from database.");
//
//            return Boolean.TRUE;
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//
//            throw new UserDeactivationException(
//                    ExceptionCodes.USER_07_USER_DEACTIVATION,
//                    "Error when trying to deactivate user. Please contact the admin."
//            );
//        }
//    }
//
//    private void validateRequestingUser(User user, String cpf, String senha) throws UserAuthenticationException {
//        if (!user.getCpf().equals(cpf) || !user.getPassword().equals(senha))
//            throw new UserAuthenticationException(
//                    ExceptionCodes.USER_09_USER_AUTHENTICATION,
//                    "User requesting User Deactivation is different from the one being deactivated. Authentication failed."
//            );
//    }
//}
