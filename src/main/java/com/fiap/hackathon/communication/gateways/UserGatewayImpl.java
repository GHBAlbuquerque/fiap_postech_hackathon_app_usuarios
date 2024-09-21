//package com.fiap.hackathon.communication.gateways;
//
//import com.fiap.hackathon.common.interfaces.gateways.UserGateway;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserGatewayImpl implements UserGateway {
//
//    @Override
//    public User saveUser(User User) {
//        final var orm = UserBuilder.fromDomainToOrm(User);
//        final var result = repository.save(orm);
//        return UserBuilder.fromOrmToDomain(result);
//    }
//
//    @Override
//    public User getUserByCpf(String cpf) {
//        final var result = repository.findByCpf(cpf);
//        return result.map(UserBuilder::fromOrmToDomain).orElse(null);
//    }
//
//    @Override
//    public User getUserById(Long id) {
//        final var result = repository.findById(id);
//        return result.map(UserBuilder::fromOrmToDomain).orElse(null);
//    }
//}
