package dev.rynwllngtn.bank.operation.deposit.application.usecase;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.application.service.AccountService;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositRequestDto;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositResponseDto;
import dev.rynwllngtn.bank.operation.deposit.application.mapper.DepositMapper;
import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepositUseCase {

    private final DepositMapper depositMapper;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Transactional
    public DepositResponseDto execute(DepositRequestDto requestDto) {
        AccountResponseDto accountDto = accountService.deposit(
                requestDto.accountId(), requestDto.amount()
        );
        UUID correlationId = UUID.randomUUID();
        TransactionResponseDto transactionDto = transactionService.credit(
                accountDto.id(), correlationId, requestDto.amount()
        );
        LocalDateTime timestamp = LocalDateTime.now();
        return depositMapper.toResponseFromAccountAndTransactionDto(accountDto, transactionDto, timestamp);
    }

}