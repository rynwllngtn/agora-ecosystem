package dev.rynwllngtn.bank.transaction.application.mapper;

import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponseDto toResponseDto(Transaction transaction) {
        return new TransactionResponseDto(transaction.getId(),
                                          transaction.getAccountId(),
                                          transaction.getCorrelationId(),
                                          transaction.getAmount(),
                                          transaction.getType());
    }

}