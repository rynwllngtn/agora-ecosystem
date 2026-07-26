package dev.rynwllngtn.bank.customer.api.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.bank.customer.application.service.CustomerService;
import dev.rynwllngtn.bank.customer.builder.CustomerBuilder;
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

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private CustomerService customerService;

    @Nested
    @DisplayName(value = "Testes de busca por ID")
    class FindByIdTests {

        @Test
        void shouldReturn200AndIdentityResponseDto() throws JsonProcessingException {
            UUID id = UUID.randomUUID();
            CustomerResponseDto mockResponseDto = CustomerBuilder.Response.valid().withId(id).build();

            when(customerService.findById(id)).thenReturn(mockResponseDto);

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.get().uri("/customer/{id}", id);

            assertThat(result).hasStatusOk();
            assertThat(result).bodyJson().isEqualTo(objectMapper.writeValueAsString(mockResponseDto));
        }

        @Test
        void shouldReturn404whenIdentityDoesNotExist() {
            UUID id = UUID.randomUUID();

            when(customerService.findById(id)).thenThrow(new ResourceNotFoundException("Customer não encontrado!"));

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.get().uri("/customer/{id}", id);

            assertThat(result).hasStatus(404);
            assertThat(result).bodyJson().extractingPath("$.detail").isEqualTo("Customer não encontrado!");
            assertThat(result).bodyJson().extractingPath("$.timestamp").isNotNull();
        }

    }

}