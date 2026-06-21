package dev.rynwllngtn.identity.builder;

import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdateEmailRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdatePasswordRequestDto;
import dev.rynwllngtn.identity.domain.Identity;

public class IdentityBuilder {

    public static String defaultCpf = "11111111111";
    public static String defaultEmail = "test@email.com";
    public static String defaultPassword = "password";

    public static String updateEmail = "testNew@email.com";
    public static String updatePassword = "newPassword";

    public static String wrongEmail = "testWrong@email.com";
    public static String wrongPassword = "wrongPassword";

    private IdentityBuilder() {
    }

    public static class Entity {

        private String cpf = defaultCpf;
        private String email = defaultEmail;
        private String password = defaultPassword;

        private Entity() {
        }

        private Entity(String cpf, String email, String password) {
            this.cpf = cpf;
            this.email = email;
            this.password = password;
        }

        public static Entity valid() {
            return new Entity();
        }

        public Identity build() {
            return new Identity(cpf, email, password);
        }

        public static Entity fromCreateRequest(IdentityCreateRequestDto createRequestDto) {
            return new Entity(createRequestDto.cpf(), createRequestDto.email(), createRequestDto.password());
        }

    }

    public static class Response {

        private Response() {}

        public static IdentityResponseDto fromEntity(Identity identity) {
            return new IdentityResponseDto(
                    identity.getId(),
                    identity.getEmail(),
                    identity.getStatus()
            );
        }

    }

    public static class CreateRequest {

        private String cpf = defaultCpf;
        private String email = defaultEmail;
        private String password = defaultPassword;

        private CreateRequest() {}

        public static CreateRequest valid() {
            return new CreateRequest();
        }

        public IdentityCreateRequestDto build() {
            return new IdentityCreateRequestDto(cpf, email, password);
        }

        public CreateRequest withInvalidEmail() {
            this.email = wrongEmail;
            return this;
        }

    }

    public static class UpdatePasswordRequest {

        private String oldPassword = defaultPassword;
        private String newPassword = updatePassword;
        private String newPasswordConfirmation = updatePassword;

        private UpdatePasswordRequest() {}

        public static UpdatePasswordRequest valid() {
            return new UpdatePasswordRequest();
        }

        public IdentityUpdatePasswordRequestDto build() {
            return new IdentityUpdatePasswordRequestDto(oldPassword, newPassword, newPasswordConfirmation);
        }

        public UpdatePasswordRequest withPasswordsNotMatching() {
            this.newPasswordConfirmation = wrongPassword;
            return this;
        }
        
        public UpdatePasswordRequest withWrongOldPassword() {
            this.oldPassword = wrongPassword;
            return this;
        }
    }

    public static class UpdateEmailRequest {

        private String newEmail = updateEmail;
        private String password = defaultPassword;

        private UpdateEmailRequest() {}

        public static UpdateEmailRequest valid() {
            return new UpdateEmailRequest();
        }

        public IdentityUpdateEmailRequestDto build() {
            return new IdentityUpdateEmailRequestDto(newEmail, password);
        }

        public UpdateEmailRequest withWrongPassword() {
            this.password = wrongPassword;
            return this;
        }

    }

}