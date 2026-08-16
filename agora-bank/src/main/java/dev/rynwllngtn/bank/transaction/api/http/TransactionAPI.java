package dev.rynwllngtn.bank.transaction.api.http;

import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(
        name = "Endpoints para Transaction"
)
@RequestMapping(value = "/transactions")
public interface TransactionAPI {

    @Operation(
            summary = "Rota para leitura de Transaction pelo ID",
            operationId = "findById"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Transaction encontrado",
            content = @Content(
                    schema = @Schema(
                            implementation = TransactionResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Transaction não encontrado no banco de dados",
            content = @Content
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    default ResponseEntity<TransactionResponseDto> findById(
            @Parameter(
                    description = "ID do Transaction para busca",
                    required = true
            )
            @PathVariable(name = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

}