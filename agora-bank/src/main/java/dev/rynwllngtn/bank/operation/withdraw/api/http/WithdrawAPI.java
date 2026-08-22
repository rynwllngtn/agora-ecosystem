package dev.rynwllngtn.bank.operation.withdraw.api.http;

import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawRequestDto;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawResponseDto;
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
        name = "APIs para saque"
)
@RequestMapping(value = "/operations")
public interface WithdrawAPI {

    @Operation(
            summary = "Rota para criação de saque em account",
            operationId = "withdraw"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Saque realizado",
            content = @Content(
                    schema = @Schema(
                            implementation = WithdrawResponseDto.class
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
    @PostMapping(value = "/withdraw")
    default ResponseEntity<WithdrawResponseDto> withdraw(
            @Parameter(
                    description = "RequestBody com dados de conta e valor de saque",
                    required = true
            )
            @RequestBody @Valid WithdrawRequestDto requestDto) {
        throw new UnsupportedOperationException();
    }

}