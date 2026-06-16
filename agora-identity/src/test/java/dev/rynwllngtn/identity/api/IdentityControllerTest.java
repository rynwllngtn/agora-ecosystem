package dev.rynwllngtn.identity.api;

import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.identity.application.service.IdentityService;
import dev.rynwllngtn.identity.domain.IdentityStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(IdentityController.class)
class IdentityControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private IdentityService identityService;

    @Test
    void shouldReturn200AndIdentityResponseDto() {
        UUID id = UUID.randomUUID();
        IdentityResponseDto responseDto = new IdentityResponseDto(id, "email@email.com", IdentityStatus.ACTIVE);

        when(identityService.findById(id)).thenReturn(responseDto);

        MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.get().uri("/identity/{id}", id);

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.id").isEqualTo(responseDto.id().toString());
        assertThat(result).bodyJson().extractingPath("$.email").isEqualTo(responseDto.email());
        assertThat(result).bodyJson().extractingPath("$.status").isEqualTo(responseDto.status().toString());
    }

    @Test
    void shouldReturn404whenIdentityDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(identityService.findById(id)).thenThrow(new ResourceNotFoundException("Identity não encontrada!"));

        MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.get().uri("/identity/{id}", id);

        assertThat(result).hasStatus(404);
        assertThat(result).bodyJson().extractingPath("$.detail").isEqualTo("Identity não encontrada!");
        assertThat(result).bodyJson().extractingPath("$.timestamp").isNotNull();
    }

}