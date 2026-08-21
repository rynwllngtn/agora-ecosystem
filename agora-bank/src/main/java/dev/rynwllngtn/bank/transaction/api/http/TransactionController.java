package dev.rynwllngtn.bank.transaction.api.http;

import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class TransactionController implements TransactionAPI {

    private final TransactionService transactionService;

    @Override
    public ResponseEntity<TransactionResponseDto> findById(UUID id) {
        TransactionResponseDto responseDto = transactionService.findById(id);
        return ResponseEntity.ok().body(responseDto);
    }

}