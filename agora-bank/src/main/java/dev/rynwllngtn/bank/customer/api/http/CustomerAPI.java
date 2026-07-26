package dev.rynwllngtn.bank.customer.api.http;

import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
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
        name = "Endpoints para Customer"
)
@RequestMapping(value = "/customer")
public interface CustomerAPI {

    @Operation(
            summary = "Rota para leitura de Customer pelo ID",
            operationId = "findById"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Customer encontrado",
            content = @Content(
                    schema = @Schema(
                            implementation = CustomerResponseDto.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Customer não encontrado no banco de dados",
            content = @Content
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    default ResponseEntity<CustomerResponseDto> findById(
            @Parameter(
                    description = "ID do Customer para busca",
                    required = true
            )
            @PathVariable(value = "id") UUID id) {
        throw new UnsupportedOperationException();
    }

}