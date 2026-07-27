package dev.rynwllngtn.bank.customer.builder;

import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.domain.Customer;
import dev.rynwllngtn.bank.customer.domain.CustomerStatus;
import dev.rynwllngtn.common.domain.TimestampedEntity;
import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerBuilder {

    public static UUID defaultIdentityId = UUID.randomUUID();
    public static String defaultCpf = "94763691082";
    public static String defaultEmail = "test@email.com";

    public static String updateEmail = "test@email.com";

    private CustomerBuilder() {
    }

    public static class Entity {

        private UUID identityId = defaultIdentityId;
        private String cpf = defaultCpf;
        private String email = defaultEmail;

        private Entity() {
        }

        public static Entity valid() {
            return new Entity();
        }

        public Customer build() {
            return new Customer(identityId, cpf, email);
        }

    }

    public static class Response {

        private UUID id = UUID.randomUUID();
        private String email = defaultEmail;
        private CustomerStatus status = CustomerStatus.PENDING_REGISTRATION;

        private Response() {}

        public static Response valid() {
            return new Response();
        }

        public Response withId(UUID id) {
            this.id = id;
            return this;
        }

        public CustomerResponseDto build() {
            return new CustomerResponseDto(id, email, status);
        }

        public static CustomerResponseDto fromEntity(Customer customer) {
            return new CustomerResponseDto(
                    customer.getId(),
                    customer.getEmail(),
                    customer.getStatus()
            );
        }

    }

    public static class CreateEvent {

        private UUID identityId = defaultIdentityId;
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

        private UUID identityId = defaultIdentityId;
        private String email = updateEmail;

        private UpdatedEmailEvent() {}

        public static UpdatedEmailEvent valid() {
            return new UpdatedEmailEvent();
        }

        public IdentityEmailUpdatedEvent build() {
            return new IdentityEmailUpdatedEvent(identityId, email, LocalDateTime.now());
        }

    }

    public static <T extends TimestampedEntity> void updatedAt(T customer, LocalDateTime updatedAt) {
        ReflectionTestUtils.setField(customer, "updatedAt", updatedAt);
    }

}