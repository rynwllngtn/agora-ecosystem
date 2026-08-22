package dev.rynwllngtn.bank.operation.deposit.api.http;

import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositRequestDto;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositResponseDto;
import dev.rynwllngtn.bank.operation.deposit.application.usecase.DepositUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DepositController implements DepositAPI {

    private final DepositUseCase depositUseCase;

    @Override
    public ResponseEntity<DepositResponseDto> deposit(DepositRequestDto requestDto) {
        DepositResponseDto responseDto = depositUseCase.execute(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

}