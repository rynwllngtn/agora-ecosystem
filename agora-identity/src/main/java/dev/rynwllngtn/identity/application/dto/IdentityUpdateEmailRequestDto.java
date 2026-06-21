package dev.rynwllngtn.identity.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IdentityUpdateEmailRequestDto(
        @NotBlank(message = "O email não pode ser vazio")
        @Email(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$",
                message = "O email deve ser válido"
        )
        String newEmail,
        @NotBlank(message = "A senha não pode ser vazio")
        @Size(
                min = 8, max = 24,
                message = "O tamanho do senha deve ser entre 8 e 24 dígitos"
        )
        String password
) {}