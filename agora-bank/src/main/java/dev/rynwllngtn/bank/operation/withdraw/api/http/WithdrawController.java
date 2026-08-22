package dev.rynwllngtn.bank.operation.withdraw.api.http;

import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawRequestDto;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawResponseDto;
import dev.rynwllngtn.bank.operation.withdraw.application.usecase.WithdrawUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WithdrawController implements WithdrawAPI {

    private final WithdrawUseCase withdrawUseCase;

    @Override
    public ResponseEntity<WithdrawResponseDto> withdraw(WithdrawRequestDto requestDto) {
        WithdrawResponseDto responseDto = withdrawUseCase.execute(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

}