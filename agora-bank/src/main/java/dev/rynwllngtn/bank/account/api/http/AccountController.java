package dev.rynwllngtn.bank.account.api.http;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AccountController implements AccountAPI {

    private final AccountService accountService;

    @Override
    public ResponseEntity<AccountResponseDto> findById(UUID id) {
        AccountResponseDto responseDto = accountService.findById(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<AccountResponseDto> activate(UUID id) {
        AccountResponseDto responseDto = accountService.activate(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<AccountResponseDto> deactivate(UUID id) {
        AccountResponseDto responseDto = accountService.deactivate(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<AccountResponseDto> suspend(UUID id) {
        AccountResponseDto responseDto = accountService.suspend(id);
        return ResponseEntity.ok().body(responseDto);
    }

}