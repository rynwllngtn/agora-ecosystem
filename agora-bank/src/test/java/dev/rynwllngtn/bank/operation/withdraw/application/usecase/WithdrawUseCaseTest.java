package dev.rynwllngtn.bank.operation.withdraw.application.usecase;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.application.service.AccountService;
import dev.rynwllngtn.bank.account.builder.AccountBuilder;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawRequestDto;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawResponseDto;
import dev.rynwllngtn.bank.operation.withdraw.application.mapper.WithdrawMapper;
import dev.rynwllngtn.bank.operation.withdraw.builder.WithdrawBuilder;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WithdrawUseCaseTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private WithdrawMapper withdrawMapper;

    @InjectMocks
    private WithdrawUseCase withdrawUseCase;

    @Test
    void shouldExecuteWithdrawSuccessfully() {
        WithdrawRequestDto requestDto = WithdrawBuilder.Request.valid().build();
        AccountResponseDto accountDto = AccountBuilder.Response.valid().withId(requestDto.accountId()).build();
        TransactionResponseDto transactionDto = TransactionBuilder.Response.valid().build();
        WithdrawResponseDto expectedResponse = WithdrawBuilder.Response.valid().build();

        when(accountService.withdraw(requestDto.accountId(), requestDto.amount())).thenReturn(accountDto);
        when(transactionService.debit(eq(accountDto.id()), any(UUID.class), eq(requestDto.amount()))).thenReturn(transactionDto);
        when(withdrawMapper.toResponseFromAccountAndTransactionDto(eq(accountDto), eq(transactionDto), any(LocalDateTime.class))).thenReturn(expectedResponse);

        WithdrawResponseDto result = withdrawUseCase.execute(requestDto);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(accountService).withdraw(requestDto.accountId(), requestDto.amount());
        verify(transactionService).debit(eq(accountDto.id()), any(UUID.class), eq(requestDto.amount()));
        verify(withdrawMapper).toResponseFromAccountAndTransactionDto(eq(accountDto), eq(transactionDto), any(LocalDateTime.class));
    }
}
