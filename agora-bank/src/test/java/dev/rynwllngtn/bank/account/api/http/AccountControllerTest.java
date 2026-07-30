package dev.rynwllngtn.bank.account.api.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.application.service.AccountService;
import dev.rynwllngtn.bank.account.builder.AccountBuilder;
import dev.rynwllngtn.bank.shared.application.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private AccountService accountService;

    @Nested
    @DisplayName("Testes de busca por ID")
    class FindByIdTests {

        @Test
        void shouldReturn200AndAccountResponseDto() throws JsonProcessingException {
            UUID id = UUID.randomUUID();
            AccountResponseDto mockResponseDto = AccountBuilder.Response.valid().withId(id).build();

            when(accountService.findById(id)).thenReturn(mockResponseDto);

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.get().uri("/account/{id}", id);

            assertThat(result).hasStatusOk();
            assertThat(result).bodyJson().isEqualTo(objectMapper.writeValueAsString(mockResponseDto));
        }

        @Test
        void shouldReturn404WhenAccountDoesNotExist() {
            UUID id = UUID.randomUUID();

            when(accountService.findById(id)).thenThrow(new ResourceNotFoundException("Account não encontrado!"));

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.get().uri("/account/{id}", id);

            assertThat(result).hasStatus(404);
            assertThat(result).bodyJson().extractingPath("$.detail").isEqualTo("Account não encontrado!");
            assertThat(result).bodyJson().extractingPath("$.timestamp").isNotNull();
        }
    }

    @Nested
    @DisplayName("Testes de mudança de status para ativo")
    class ActivateTests {
        @Test
        void shouldReturn200WhenActivated() {
            testStatusChange("activate");
        }
    }

    @Nested
    @DisplayName("Testes de mudança de status para inativo")
    class DeactivateTests {
        @Test
        void shouldReturn200WhenDeactivated() {
            testStatusChange("deactivate");
        }
    }

    @Nested
    @DisplayName("Testes de mudança de status para suspenso")
    class SuspendTests {
        @Test
        void shouldReturn200WhenSuspended() {
            testStatusChange("suspend");
        }
    }

    private void testStatusChange(String statusPath) {
        UUID id = UUID.randomUUID();
        AccountResponseDto mockResponseDto = AccountBuilder.Response.valid().withId(id).build();

        when(accountService.activate(id)).thenReturn(mockResponseDto);
        when(accountService.deactivate(id)).thenReturn(mockResponseDto);
        when(accountService.suspend(id)).thenReturn(mockResponseDto);

        MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.patch().uri("/account/{id}/{statusPath}", id, statusPath);

        assertThat(result).hasStatusOk();
    }

}