package dev.rynwllngtn.bank.customer.application.service;

import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.shared.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.bank.customer.application.mapper.CustomerMapper;
import dev.rynwllngtn.bank.customer.builder.CustomerBuilder;
import dev.rynwllngtn.bank.customer.domain.Customer;
import dev.rynwllngtn.bank.customer.domain.CustomerRepository;
import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplementationTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImplementation customerService;

    @Nested
    @DisplayName(value = "Testes de busca por ID")
    class FindByIdTests {

        @Test
        void shouldReturnCustomerResponseDto() {
            Customer mockCustomer = CustomerBuilder.Entity.valid().build();
            CustomerResponseDto mockResponseDto = CustomerBuilder.Response.fromEntity(mockCustomer);
            UUID id = mockCustomer.getId();

            when(customerRepository.findById(id)).thenReturn(Optional.of(mockCustomer));
            when(customerMapper.toResponseDto(mockCustomer)).thenReturn(mockResponseDto);

            CustomerResponseDto result = customerService.findById(id);

            assertNotNull(result);
            assertEquals(mockResponseDto.id(), result.id());
            assertEquals(mockResponseDto.email(), result.email());
            assertEquals(mockResponseDto.status(), result.status());

            verify(customerRepository).findById(id);
            verify(customerMapper).toResponseDto(mockCustomer);
        }

        @Test
        void shouldThrowResourceNotFoundException() {
            UUID nonExistentId = UUID.randomUUID();
            when(customerRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                customerService.findById(nonExistentId);
            });

            verify(customerRepository).findById(nonExistentId);
        }

    }

    @Nested
    @DisplayName(value = "Testes de criação")
    class CreateTests {

        @Test
        void shouldCreateCustomerWhenIdentityDoesNotExist() {
            IdentityCreatedEvent mockCreatedEvent = CustomerBuilder.CreateEvent.valid().build();
            Customer mockCustomer = CustomerBuilder.Entity.valid().build();
            UUID identityId = mockCreatedEvent.id();

            when(customerRepository.existsByIdentityId(identityId)).thenReturn(false);
            when(customerMapper.toEntity(mockCreatedEvent)).thenReturn(mockCustomer);

            customerService.create(mockCreatedEvent);

            verify(customerRepository).existsByIdentityId(mockCustomer.getIdentityId());
            verify(customerMapper).toEntity(mockCreatedEvent);
            verify(customerRepository).save(mockCustomer);
        }

        @Test
        void shouldNotCreateCustomerWhenIdentityExist() {
            IdentityCreatedEvent mockCreatedEvent = CustomerBuilder.CreateEvent.valid().build();
            UUID identityId = mockCreatedEvent.id();

            when(customerRepository.existsByIdentityId(identityId)).thenReturn(true);

            customerService.create(mockCreatedEvent);

            verify(customerRepository).existsByIdentityId(identityId);
            verify(customerMapper, never()).toEntity(any());
            verify(customerRepository, never()).save(any());
        }

    }

    @Nested
    @DisplayName(value = "Testes de atualização de email")
    class UpdateEmailTests {

        @Test
        void shouldUpdateEmail() {
            IdentityEmailUpdatedEvent mockEmailUpdateEvent = CustomerBuilder.UpdatedEmailEvent.valid().build();
            Customer customer = CustomerBuilder.Entity.valid().build();
            CustomerBuilder.updatedAt(customer, mockEmailUpdateEvent.updatedAt().minusDays(1));
            UUID identityId = mockEmailUpdateEvent.id();

            when(customerRepository.findByIdentityId(identityId)).thenReturn(Optional.of(customer));

            customerService.updateEmail(mockEmailUpdateEvent);

            assertEquals(mockEmailUpdateEvent.email(), customer.getEmail());

            verify(customerRepository).save(customer);
        }

        @Test
        void shouldIgnoreUpdateWhenEventIsOlderThanCurrentState() {
            IdentityEmailUpdatedEvent mockEmailUpdateEvent = CustomerBuilder.UpdatedEmailEvent.valid().build();
            Customer customer = CustomerBuilder.Entity.valid().build();
            CustomerBuilder.updatedAt(customer, mockEmailUpdateEvent.updatedAt().plusDays(1));
            String originalEmail = customer.getEmail();
            UUID identityId = mockEmailUpdateEvent.id();

            when(customerRepository.findByIdentityId(identityId)).thenReturn(Optional.of(customer));

            customerService.updateEmail(mockEmailUpdateEvent);

            assertEquals(originalEmail, customer.getEmail());

            verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldThrowResourceNotFoundWhenIdentityIdDoesNotMatchAnyCustomer() {
            IdentityEmailUpdatedEvent mockEmailUpdateEvent = CustomerBuilder.UpdatedEmailEvent.valid().build();
            UUID identityId = mockEmailUpdateEvent.id();

            when(customerRepository.findByIdentityId(identityId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                customerService.updateEmail(mockEmailUpdateEvent);
            });

            verify(customerRepository, never()).save(any());
        }

    }

}