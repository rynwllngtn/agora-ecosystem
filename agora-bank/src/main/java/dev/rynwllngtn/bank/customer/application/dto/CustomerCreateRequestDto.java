package dev.rynwllngtn.bank.customer.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record CustomerCreateRequestDto(
        @NotNull(message = "O identityId não pode ser nulo")
        UUID identityId,
        @NotBlank(message = "O cpf não pode ser vazio")
        @CPF(message = "O cpf deve ser válido")
        String cpf,
        @NotBlank(message = "O email não pode ser vazio")
        @Email(
                regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$",
                message = "O email deve ser válido"
        )
        String email
) {}