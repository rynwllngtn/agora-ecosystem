package dev.rynwllngtn.identity.builder;

import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;
import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdateEmailRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdatePasswordRequestDto;
import dev.rynwllngtn.identity.domain.Identity;
import dev.rynwllngtn.identity.domain.IdentityStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class IdentityBuilder {

    public static String defaultCpf = "94763691082";
    public static String defaultEmail = "test@email.com";
    public static String defaultPassword = "password";

    public static String updateEmail = "testNew@email.com";
    public static String updatePassword = "newPassword";

    public static String wrongCpf = "01234567890";
    public static String wrongEmail = "testWrong@email.com";
    public static String wrongPassword = "wrongPassword";

    public static String invalidCpf = "cpf";
    public static String invalidEmail = "email";
    public static String invalidPassword = "pass";

    public static UUID defaultId = UUID.randomUUID();

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

        private UUID id = UUID.randomUUID();
        private String email = defaultEmail;
        private IdentityStatus status = IdentityStatus.ACTIVE;

        private Response() {}

        public static Response valid() {
            return new Response();
        }

        public Response withId(UUID id) {
            this.id = id;
            return this;
        }

        public Response withEmail(String email) {
            this.email = email;
            return this;
        }

        public Response withStatus(IdentityStatus status) {
            this.status = status;
            return this;
        }

        public IdentityResponseDto build() {
            return new IdentityResponseDto(id, email, status);
        }

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

        public CreateRequest withInvalidCpf() {
            this.cpf = invalidCpf;
            return this;
        }

        public CreateRequest withInvalidEmail() {
            this.email = invalidPassword;
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

    public static class CreateEvent {

        private UUID identityId = defaultId;
        private String cpf = defaultCpf;
        private String email = defaultEmail;

        private CreateEvent() {}

        public static CreateEvent valid() {
            return new CreateEvent();
        }

        public IdentityCreatedEvent build() {
            return new IdentityCreatedEvent(identityId, cpf, email, LocalDateTime.now());
        }

    }

    public static class UpdatedEmailEvent {

        private UUID identityId = defaultId;
        private String email = updateEmail;

        private UpdatedEmailEvent() {}

        public static UpdatedEmailEvent valid() {
            return new UpdatedEmailEvent();
        }

        public IdentityEmailUpdatedEvent build() {
            return new IdentityEmailUpdatedEvent(identityId, email, LocalDateTime.now());
        }

    }

}