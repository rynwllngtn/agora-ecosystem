package dev.rynwllngtn.bank.account.application.service;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.application.mapper.AccountMapper;
import dev.rynwllngtn.bank.account.builder.AccountBuilder;
import dev.rynwllngtn.bank.account.domain.Account;
import dev.rynwllngtn.bank.account.domain.AccountRepository;
import dev.rynwllngtn.bank.shared.application.exception.ResourceNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplementationTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImplementation accountService;


    @Nested
    @DisplayName("Testes de busca por ID")
    class FindByIdTests {

        @Test
        void shouldReturnAccountResponseDto() {
            Account account = AccountBuilder.Entity.valid().build();
            AccountResponseDto accountResponseDto = AccountBuilder.Response.fromEntity(account);

            when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
            when(accountMapper.toResponseDto(account)).thenReturn(accountResponseDto);

            AccountResponseDto result = accountService.findById(account.getId());

            assertNotNull(result);
            assertEquals(accountResponseDto, result);
            verify(accountRepository).findById(account.getId());
            verify(accountMapper).toResponseDto(account);
        }

        @Test
        void shouldThrowResourceNotFoundException() {
            UUID nonExistentId = UUID.randomUUID();

            when(accountRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                accountService.findById(nonExistentId);
            });

            verify(accountRepository).findById(nonExistentId);
        }
    }

    @Nested
    @DisplayName("Testes de criação")
    class CreateAccountTests {

        @Test
        void shouldCreateAccountWithSuccess() {
            UUID customerId = UUID.randomUUID();

            when(accountRepository.existsByCustomerId(customerId)).thenReturn(false);

            accountService.create(customerId);

            verify(accountRepository).existsByCustomerId(customerId);
            verify(accountRepository).save(any());
        }

        @Test
        void shouldNotCreateAccountWhenCustomerAlreadyHasOne() {
            UUID customerId = UUID.randomUUID();

            when(accountRepository.existsByCustomerId(customerId)).thenReturn(true);

            accountService.create(customerId);

            verify(accountRepository).existsByCustomerId(customerId);
        }

    }

    @Nested
    @DisplayName("Testes de mudança de status para ativo")
    class ActivateAccountTests {

        @Test
        void shouldActivateAccountWithSuccess() {
            Account account = AccountBuilder.Entity.valid().build();
            AccountResponseDto accountResponseDto = AccountBuilder.Response.fromEntity(account);

            when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
            when(accountMapper.toResponseDto(account)).thenReturn(accountResponseDto);

            AccountResponseDto result = accountService.activate(account.getId());

            assertNotNull(result);
            assertEquals(accountResponseDto, result);
            verify(accountRepository).findById(account.getId());
            verify(accountMapper).toResponseDto(account);
        }
    }

    @Nested
    @DisplayName("Testes de mudança de status para inativo")
    class DeactivateAccountTests {

        @Test
        void shouldDeactivateAccountWithSuccess() {
            Account account = AccountBuilder.Entity.valid().build();
            AccountResponseDto accountResponseDto = AccountBuilder.Response.fromEntity(account);

            when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
            when(accountMapper.toResponseDto(account)).thenReturn(accountResponseDto);

            AccountResponseDto result = accountService.deactivate(account.getId());

            assertNotNull(result);
            assertEquals(accountResponseDto, result);
            verify(accountRepository).findById(account.getId());
            verify(accountMapper).toResponseDto(account);
        }
    }

    @Nested
    @DisplayName("Testes de mudança de status para suspenso")
    class SuspendAccountTests {

        @Test
        void shouldSuspendAccountWithSuccess() {
            Account account = AccountBuilder.Entity.valid().build();
            AccountResponseDto accountResponseDto = AccountBuilder.Response.fromEntity(account);

            when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
            when(accountMapper.toResponseDto(account)).thenReturn(accountResponseDto);

            AccountResponseDto result = accountService.suspend(account.getId());

            assertNotNull(result);
            assertEquals(accountResponseDto, result);
            verify(accountRepository).findById(account.getId());
            verify(accountMapper).toResponseDto(account);
        }
    }

}