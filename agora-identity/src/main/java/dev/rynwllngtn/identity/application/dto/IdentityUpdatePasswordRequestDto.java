package dev.rynwllngtn.identity.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IdentityUpdatePasswordRequestDto(
        @NotBlank(message = "A senha atual não pode ser vazio")
        @Size(
                min = 8, max = 24,
                message = "O tamanho do senha deve ser entre 8 e 24 dígitos"
        )
        String oldPassword,
        @NotBlank(message = "A nova senha não pode ser vazio")
        @Size(
                min = 8, max = 24,
                message = "O tamanho do senha deve ser entre 8 e 24 dígitos"
        )
        String newPassword,
        @NotBlank(message = "A nova senha não pode ser vazio")
        @Size(
                min = 8, max = 24,
                message = "O tamanho do senha deve ser entre 8 e 24 dígitos"
        )
        String newPasswordConfirmation

) {
    public boolean doesNewPasswordMatch() {
        return newPassword.equals(newPasswordConfirmation);
    }
}