package dev.rynwllngtn.bank.operation.withdraw.application.mapper;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawResponseDto;
import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WithdrawMapper {

    public WithdrawResponseDto toResponseFromAccountAndTransactionDto(
            AccountResponseDto accountDto, TransactionResponseDto transactionDto, LocalDateTime timestamp) {
        return new WithdrawResponseDto(accountDto.id(),
                                       transactionDto.id(),
                                       transactionDto.correlationId(),
                                       transactionDto.amount(),
                                       accountDto.balance(),
                                       timestamp);
    }

}