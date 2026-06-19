package dev.rynwllngtn.identity.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record IdentityCreateRequestDto(
        @NotBlank(message = "O cpf não pode ser vazio")
        @CPF(message = "O cpf deve ser válido")
        String cpf,
        @NotBlank(message = "O email não pode ser vazio")
        @Email(
                regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$",
                message = "O email deve ser válido"
        )
        String email,
        @NotBlank(message = "A senha não pode ser vazio")
        @Size(
                min = 8, max = 24,
                message = "O tamanho do senha deve ser entre 8 e 24 dígitos"
        )
        String password
) {
    public boolean isCpfUsed(String cpf) {
        return this.cpf.equals(cpf);
    }
    public boolean isEmailUsed(String email) {
        return this.email.equals(email);
    }
}