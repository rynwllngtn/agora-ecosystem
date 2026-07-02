package dev.rynwllngtn.bank.customer.builder;

import dev.rynwllngtn.bank.customer.application.dto.CustomerCreateRequestDto;
import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.domain.Customer;
import dev.rynwllngtn.bank.customer.domain.CustomerStatus;

import java.util.UUID;

public class CustomerBuilder {

    public static String defaultCpf = "94763691082";
    public static String defaultEmail = "test@email.com";

    private CustomerBuilder() {
    }

    public static class Entity {

        private String cpf = defaultCpf;
        private String email = defaultEmail;

        private Entity() {
        }

        private Entity(String cpf, String email) {
            this.cpf = cpf;
            this.email = email;
        }

        public static Entity valid() {
            return new Entity();
        }

        public Customer build() {
            return new Customer(UUID.randomUUID(), cpf, email);
        }

        public static Entity fromCreateRequest(CustomerCreateRequestDto createRequestDto) {
            return new Entity(createRequestDto.cpf(), createRequestDto.email());
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

    public static class CreateRequest {
        private String cpf = defaultCpf;
        private String email = defaultEmail;

        private CreateRequest() {}

        public static CreateRequest valid() {
            return new CreateRequest();
        }

        public CustomerCreateRequestDto build() {
            return new CustomerCreateRequestDto(UUID.randomUUID(), cpf, email);
        }

    }

}