package dev.rynwllngtn.identity.api;

import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdateEmailRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdatePasswordRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        name = "Endpoints para Identity"
)
@RequestMapping(value = "/identity")
public interface IdentityAPI {

    @Operation(
            summary = "Rota para leitura de Identity pelo ID",
            operationId = "findById"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Identity encontrado",
            content = @Content(
                    schema = @Schema(
                            implementation = IdentityResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Identity não encontrado no banco de dados",
            content = @Content
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    default ResponseEntity<IdentityResponseDto> findById(
            @Parameter(
                    description = "ID da Identity para busca",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

    @Operation(
            summary = "Rota para a criação de nova Identity",
            operationId = "save"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Identity criada com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = IdentityResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro de validação",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Identity não criada",
            content = @Content
    )
    @PostMapping(produces = "application/json")
    default ResponseEntity<IdentityResponseDto> create(
            @Parameter(
                    description = "RequestBody para criação de Identity",
                    required = true
            )
            @RequestBody @Valid IdentityCreateRequestDto createRequestDto) {
        throw new UnsupportedOperationException();
    }

    @Operation(
            summary = "Rota para atualização de senha de Identity",
            operationId = "changePassword"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Senha atualizada com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = IdentityResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro de validação",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Identity não encontrada no banco de dados",
            content = @Content
    )
    @PatchMapping(value = "/{id}/changePassword", produces = "application/json")
    default ResponseEntity<IdentityResponseDto> changePassword(
            @Parameter(
                    description = "ID da Identity para busca",
                    required = true
            )
            @PathVariable(value = "id") UUID id,
            @Parameter(
                    description = "RequestBody para atualização de senha",
                    required = true
            )
            @RequestBody @Valid IdentityUpdatePasswordRequestDto updateRequestDto) {
        throw new UnsupportedOperationException();
    }

    @Operation(
            summary = "Rota para atualização de email de Identity",
            operationId = "changeEmail"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Email atualizado com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = IdentityResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro de validação",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Identity não encontrada no banco de dados",
            content = @Content
    )
    @PatchMapping(value = "/{id}/changeEmail", produces = "application/json")
    default ResponseEntity<IdentityResponseDto> changeEmail(
            @Parameter(
                    description = "ID da Identity para busca",
                    required = true
            )
            @PathVariable(value = "id") UUID id,
            @Parameter(
                    description = "RequestBody para atualização de email",
                    required = true
            )
            @RequestBody @Valid IdentityUpdateEmailRequestDto updateRequestDto) {
        throw new UnsupportedOperationException();
    }

    @Operation(
            summary = "Rota para ativação de Identity",
            operationId = "activate"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Identity ativada com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = IdentityResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Identity não encontrada no banco de dados",
            content = @Content
    )
    @PatchMapping(value = "/{id}/activate", produces = "application/json")
    default ResponseEntity<IdentityResponseDto> activate(
            @Parameter(
                    description = "ID da Identity para busca",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

    @Operation(
            summary = "Rota para desativação de Identity",
            operationId = "deactivate"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Identity desativada com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = IdentityResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Identity não encontrada no banco de dados",
            content = @Content
    )
    @PatchMapping(value = "/{id}/deactivate", produces = "application/json")
    default ResponseEntity<IdentityResponseDto> deactivate(
            @Parameter(
                    description = "ID da Identity para busca",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

    @Operation(
            summary = "Rota para suspensão de Identity",
            operationId = "suspend"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Identity suspensa com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = IdentityResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Identity não encontrada no banco de dados",
            content = @Content
    )
    @PatchMapping(value = "/{id}/suspend", produces = "application/json")
    default ResponseEntity<IdentityResponseDto> suspend(
            @Parameter(
                    description = "ID da Identity para busca",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

}