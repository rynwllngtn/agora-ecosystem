package dev.rynwllngtn.identity.api;

import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
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
            description = "Identity não encontrado no banco de dados"
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    default ResponseEntity<IdentityResponseDto> findById(
            @Parameter(
                    description = "UUID da Identity",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

}