package dev.rynwllngtn.bank.operation.deposit.api.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositRequestDto;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositResponseDto;
import dev.rynwllngtn.bank.operation.deposit.application.usecase.DepositUseCase;
import dev.rynwllngtn.bank.operation.deposit.builder.DepositBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(DepositController.class)
class DepositControllerTest {

    private final ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private DepositUseCase depositUseCase;

    @Test
    void shouldReturnOkForValidDeposit() throws JsonProcessingException {
        DepositRequestDto requestDto = DepositBuilder.Request.valid().build();
        DepositResponseDto responseDto = DepositBuilder.Response.valid().build();

        when(depositUseCase.execute(any(DepositRequestDto.class))).thenReturn(responseDto);

        MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.post().uri("/operations/deposit")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(requestDto));

        assertThat(result).hasStatus(200);
        assertThat(result).bodyJson().isEqualTo(objectMapper.writeValueAsString(responseDto));
    }

}