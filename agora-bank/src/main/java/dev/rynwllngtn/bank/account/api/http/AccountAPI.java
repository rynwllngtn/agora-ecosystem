package dev.rynwllngtn.bank.account.api.http;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(
        name = "Endpoints para Account"
)
@RequestMapping(value = "/account")
public interface AccountAPI {

    @Operation(
            summary = "Rota para leitura de Account pelo ID",
            operationId = "findById"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account encontrado",
            content = @Content(
                    schema = @Schema(
                            implementation = AccountResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Account não encontrada no banco de dados",
            content = @Content
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    default ResponseEntity<AccountResponseDto> findById(
            @Parameter(
                    description = "ID da Account para busca",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

    @Operation(
            summary = "Rota para ativação de Account",
            operationId = "activate"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account ativada com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = AccountResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Account não encontrada no banco de dados",
            content = @Content
    )
    @PatchMapping(value = "/{id}/activate", produces = "application/json")
    default ResponseEntity<AccountResponseDto> activate(
            @Parameter(
                    description = "ID da Account para ativação",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

    @Operation(
            summary = "Rota para desativação de Account",
            operationId = "activate"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account desativada com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = AccountResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Account não encontrada no banco de dados",
            content = @Content
    )
    @PatchMapping(value = "/{id}/deactivate", produces = "application/json")
    default ResponseEntity<AccountResponseDto> deactivate(
            @Parameter(
                    description = "ID da Account para desativação",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

    @Operation(
            summary = "Rota para suspensão de Account",
            operationId = "activate"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account suspensa com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = AccountResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Account não encontrada no banco de dados",
            content = @Content
    )
    @PatchMapping(value = "/{id}/suspend", produces = "application/json")
    default ResponseEntity<AccountResponseDto> suspend(
            @Parameter(
                    description = "ID da Account para suspensão",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

}