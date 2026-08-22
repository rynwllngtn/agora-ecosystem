package dev.rynwllngtn.bank.operation.deposit.api.http;

import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositRequestDto;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
        name = "APIs para depósito"
)
@RequestMapping(value = "/operations")
public interface DepositAPI {

    @Operation(
            summary = "Rota para criação de depósito em account",
            operationId = "deposit"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Deposito realizado",
            content = @Content(
                    schema = @Schema(
                            implementation = DepositResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro de validação (ex: valor negativo ou nulo)",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Account não encontrada no banco de dados",
            content = @Content
    )
    @ApiResponse(
            responseCode = "422",
            description = "Regra de negócio violada (ex: conta inativa, saldo insuficiente)",
            content = @Content
    )
    @PostMapping(value = "/deposit")
    default ResponseEntity<DepositResponseDto> deposit(
            @Parameter(
                    description = "RequestBody com dados de conta e valor de depósito",
                    required = true
            )
            @RequestBody @Valid DepositRequestDto requestDto) {
        throw new UnsupportedOperationException();
    }

}