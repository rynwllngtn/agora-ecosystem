package dev.rynwllngtn.bank.operation.withdraw.application.usecase;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.application.service.AccountService;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawRequestDto;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawResponseDto;
import dev.rynwllngtn.bank.operation.withdraw.application.mapper.WithdrawMapper;
import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WithdrawUseCase {

    private final WithdrawMapper withdrawMapper;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Transactional
    public WithdrawResponseDto execute(WithdrawRequestDto requestDto) {
        AccountResponseDto accountDto = accountService.withdraw(
                requestDto.accountId(), requestDto.amount()
        );
        UUID correlationId = UUID.randomUUID();
        TransactionResponseDto transactionDto = transactionService.debit(
                accountDto.id(), correlationId, requestDto.amount()
        );
        LocalDateTime timestamp = LocalDateTime.now();
        return withdrawMapper.toResponseFromAccountAndTransactionDto(accountDto, transactionDto, timestamp);
    }

}