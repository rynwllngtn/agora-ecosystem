package dev.rynwllngtn.bank.transaction.api.http;

import dev.rynwllngtn.bank.shared.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.application.service.TransactionService;
import dev.rynwllngtn.bank.transaction.builder.TransactionBuilder;
import dev.rynwllngtn.bank.transaction.domain.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private TransactionService transactionService;

    @Nested
    @DisplayName(value = "Testes de busca por ID")
    class FindByIdTests {

        @Test
        void shouldReturn200AndTransactionResponseDto() {
            UUID id = UUID.randomUUID();
            TransactionResponseDto responseDto = TransactionBuilder.Response.validOfType(TransactionType.DEBIT).withId(id).build();

            when(transactionService.findById(id)).thenReturn(responseDto);

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.get().uri("/transactions/{id}", id);

            assertThat(result).hasStatusOk();
            assertThat(result).bodyJson().isEqualTo(objectMapper.writeValueAsString(responseDto));
        }


        @Test
        void shouldReturn404WhenTransactionDoesNotExist() {
            UUID id = UUID.randomUUID();

            when(transactionService.findById(id)).thenThrow(new ResourceNotFoundException("Transaction não econtrado!"));

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.get().uri("/transactions/{id}", id);

            assertThat(result).hasStatus(404);
            assertThat(result).bodyJson().extractingPath("$.detail").isEqualTo("Transaction não econtrado!");
            assertThat(result).bodyJson().extractingPath("$.timestamp").isNotNull();
        }

    }

}