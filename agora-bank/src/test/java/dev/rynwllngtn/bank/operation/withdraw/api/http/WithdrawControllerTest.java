package dev.rynwllngtn.bank.operation.withdraw.api.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawRequestDto;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawResponseDto;
import dev.rynwllngtn.bank.operation.withdraw.application.usecase.WithdrawUseCase;
import dev.rynwllngtn.bank.operation.withdraw.builder.WithdrawBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(WithdrawController.class)
class WithdrawControllerTest {

    private final ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private WithdrawUseCase withdrawUseCase;

    @Test
    void shouldReturnOkForValidWithdraw() throws JsonProcessingException {
        WithdrawRequestDto requestDto = WithdrawBuilder.Request.valid().build();
        WithdrawResponseDto responseDto = WithdrawBuilder.Response.valid().build();

        when(withdrawUseCase.execute(any(WithdrawRequestDto.class))).thenReturn(responseDto);

        MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.post().uri("/operations/withdraw")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(requestDto));

        assertThat(result).hasStatus2xxSuccessful();
        assertThat(result).bodyJson().isEqualTo(objectMapper.writeValueAsString(responseDto));
    }

}