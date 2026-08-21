package dev.rynwllngtn.bank.transaction.application.mapper;

import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.builder.TransactionBuilder;
import dev.rynwllngtn.bank.transaction.domain.Transaction;
import dev.rynwllngtn.bank.transaction.domain.TransactionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionMapperTest {

    private final TransactionMapper transactionMapper = new TransactionMapper();

    @Test
    void shouldMapTransactionToTransactionResponseDto() {
        Transaction transaction = TransactionBuilder.Entity.validOfType(TransactionType.CREDIT).build();

        TransactionResponseDto responseDto = transactionMapper.toResponseDto(transaction);

        assertNotNull(responseDto);
        assertEquals(transaction.getId(), responseDto.id());
        assertEquals(transaction.getAccountId(), responseDto.accountId());
        assertEquals(transaction.getCorrelationId(), responseDto.correlationId());
        assertEquals(transaction.getAmount(), responseDto.amount());
        assertEquals(transaction.getType(), responseDto.type());
    }

}