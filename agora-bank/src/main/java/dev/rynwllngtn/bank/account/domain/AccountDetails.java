package dev.rynwllngtn.bank.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record AccountDetails(
        @Column(name = "agency", nullable = false)
        String agency,
        @Column(name = "number", nullable = false, unique = true)
        String number,
        @Column(name = "bank_code", nullable = false)
        String bankCode
) {
    public AccountDetails(String number) {
        this(AccountConstants.AGENCY, number, AccountConstants.BANK_CODE);
    }
}