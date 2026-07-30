package dev.rynwllngtn.bank.account.application.mapper;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.domain.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountResponseDto toResponseDto(Account account) {
        return new AccountResponseDto(account.getId(),
                                      account.getCustomerId(),
                                      account.getDetails().agency(),
                                      account.getDetails().number(),
                                      account.getDetails().bankCode(),
                                      account.getStatus(),
                                      account.getBalance());
    }

}