package dev.rynwllngtn.bank.account.application.mapper;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.builder.AccountBuilder;
import dev.rynwllngtn.bank.account.domain.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountMapperTest {

    private final AccountMapper accountMapper = new AccountMapper();

    @Test
    void shouldMapAccountToAccountResponseDto() {
        Account account = AccountBuilder.Entity.valid().build();

        AccountResponseDto responseDto = accountMapper.toResponseDto(account);

        assertNotNull(responseDto);
        assertEquals(account.getId(), responseDto.id());
        assertEquals(account.getCustomerId(), responseDto.customerId());
        assertEquals(account.getDetails().agency(), responseDto.agency());
        assertEquals(account.getDetails().number(), responseDto.number());
        assertEquals(account.getDetails().bankCode(), responseDto.bankCode());
        assertEquals(account.getStatus(), responseDto.status());
        assertEquals(account.getBalance(), responseDto.balance());
    }

}