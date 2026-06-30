package dev.rynwllngtn.bank.customer.application.service;

import dev.rynwllngtn.bank.customer.application.dto.CustomerCreateRequestDto;
import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.bank.customer.application.mapper.CustomerMapper;
import dev.rynwllngtn.bank.customer.builder.CustomerBuilder;
import dev.rynwllngtn.bank.customer.domain.Customer;
import dev.rynwllngtn.bank.customer.domain.CustomerRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        void shouldCreateCustomerAndSave() {
            CustomerCreateRequestDto mockCreateRequestDto = CustomerBuilder.CreateRequest.valid().build();
            Customer mockCustomer = CustomerBuilder.Entity.valid().build();
            CustomerResponseDto mockResponseDto = CustomerBuilder.Response.fromEntity(mockCustomer);

            when(customerMapper.toEntity(mockCreateRequestDto)).thenReturn(mockCustomer);
            when(customerRepository.save(mockCustomer)).thenReturn(mockCustomer);
            when(customerMapper.toResponseDto(mockCustomer)).thenReturn(mockResponseDto);

            CustomerResponseDto result = customerService.create(mockCreateRequestDto);

            assertNotNull(result);
            assertEquals(mockResponseDto.id(), result.id());
            assertEquals(mockResponseDto.email(), result.email());

            verify(customerMapper).toEntity(mockCreateRequestDto);
            verify(customerRepository).save(mockCustomer);
            verify(customerMapper).toResponseDto(mockCustomer);
        }

    }

}