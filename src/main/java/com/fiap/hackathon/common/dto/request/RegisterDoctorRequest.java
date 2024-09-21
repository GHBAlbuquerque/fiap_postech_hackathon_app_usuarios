package com.fiap.hackathon.common.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegisterDoctorRequest extends RegisterUserRequest {

    @NotBlank
    private String crm;

    @NotNull
    private String medicalSpecialty;

    public RegisterDoctorRequest(@NotBlank String name, @NotNull LocalDate birthday, @NotBlank @Size(min = 11, message = "CPF deve ter 11 caracteres") String cpf, @NotBlank @Email(message = "E-mail deve ser válido") String email, @NotBlank @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres") String password, @NotBlank @Size(min = 11, message = "Telefone (com DDD) deve ter 11 caracteres") String contactNumber) {
        super(name, birthday, cpf, email, password, contactNumber);
    }
}
