package dev.rynwllngtn.bank.operation.deposit.application.usecase;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.application.service.AccountService;
import dev.rynwllngtn.bank.account.builder.AccountBuilder;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositRequestDto;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositResponseDto;
import dev.rynwllngtn.bank.operation.deposit.application.mapper.DepositMapper;
import dev.rynwllngtn.bank.operation.deposit.builder.DepositBuilder;
import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.application.service.TransactionService;
import dev.rynwllngtn.bank.transaction.builder.TransactionBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositUseCaseTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private DepositMapper depositMapper;

    @InjectMocks
    private DepositUseCase depositUseCase;

    @Test
    void shouldExecuteDepositSuccessfully() {
        DepositRequestDto requestDto = DepositBuilder.Request.valid().build();
        AccountResponseDto accountDto = AccountBuilder.Response.valid().withId(requestDto.accountId()).build();
        TransactionResponseDto transactionDto = TransactionBuilder.Response.valid().build();
        DepositResponseDto expectedResponse = DepositBuilder.Response.valid().build();

        when(accountService.deposit(requestDto.accountId(), requestDto.amount())).thenReturn(accountDto);
        when(transactionService.credit(eq(accountDto.id()), any(UUID.class), eq(requestDto.amount()))).thenReturn(transactionDto);
        when(depositMapper.toResponseFromAccountAndTransactionDto(eq(accountDto), eq(transactionDto), any(LocalDateTime.class))).thenReturn(expectedResponse);

        DepositResponseDto result = depositUseCase.execute(requestDto);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        
        verify(accountService).deposit(requestDto.accountId(), requestDto.amount());
        verify(transactionService).credit(eq(accountDto.id()), any(UUID.class), eq(requestDto.amount()));
        verify(depositMapper).toResponseFromAccountAndTransactionDto(eq(accountDto), eq(transactionDto), any(LocalDateTime.class));
    }

}