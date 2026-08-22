package dev.rynwllngtn.bank.operation.withdraw.application.mapper;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.builder.AccountBuilder;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawResponseDto;
import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.builder.TransactionBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WithdrawMapperTest {

    private final WithdrawMapper withdrawMapper = new WithdrawMapper();

    @Test
    void shouldMapToWithdrawResponseDto() {
        AccountResponseDto accountDto = AccountBuilder.Response.valid().build();
        TransactionResponseDto transactionDto = TransactionBuilder.Response.valid().build();
        LocalDateTime timestamp = LocalDateTime.now();

        WithdrawResponseDto result =
                withdrawMapper.toResponseFromAccountAndTransactionDto(accountDto, transactionDto, timestamp);

        assertNotNull(result);
        assertEquals(accountDto.id(), result.accountId());
        assertEquals(transactionDto.id(), result.transactionId());
        assertEquals(transactionDto.correlationId(), result.correlationId());
        assertEquals(accountDto.balance(), result.newBalance());
        assertEquals(transactionDto.amount(), result.amount());
        assertEquals(timestamp, result.timestamp());
    }

}