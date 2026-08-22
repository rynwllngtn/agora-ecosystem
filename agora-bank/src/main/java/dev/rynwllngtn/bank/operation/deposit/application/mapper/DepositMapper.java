package dev.rynwllngtn.bank.operation.deposit.application.mapper;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositResponseDto;
import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DepositMapper {

    public DepositResponseDto toResponseFromAccountAndTransactionDto(
            AccountResponseDto accountDto, TransactionResponseDto transactionDto, LocalDateTime timestamp) {
        return new DepositResponseDto(accountDto.id(),
                                      transactionDto.id(),
                                      transactionDto.correlationId(),
                                      accountDto.balance(),
                                      transactionDto.amount(),
                                      timestamp);
    }

}