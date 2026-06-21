package dev.rynwllngtn.identity.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdateEmailRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdatePasswordRequestDto;
import dev.rynwllngtn.identity.application.exception.DuplicateResourceException;
import dev.rynwllngtn.identity.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.identity.application.exception.WrongPasswordException;
import dev.rynwllngtn.identity.application.service.IdentityService;
import dev.rynwllngtn.identity.builder.IdentityBuilder;
import dev.rynwllngtn.identity.domain.Identity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(IdentityController.class)
class IdentityControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private IdentityService identityService;

    @Nested
    @DisplayName(value = "Testes de busca por ID")
    class FindByIdTests {

        @Test
        void shouldReturn200AndIdentityResponseDto() throws JsonProcessingException {
            UUID id = UUID.randomUUID();
            IdentityResponseDto mockResponseDto = IdentityBuilder.Response.valid().withId(id).build();

            when(identityService.findById(id)).thenReturn(mockResponseDto);

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.get().uri("/identity/{id}", id);

            assertThat(result).hasStatusOk();
            assertThat(result).bodyJson().isEqualTo(objectMapper.writeValueAsString(mockResponseDto));
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

    @Nested
    @DisplayName(value = "Testes de criação")
    class CreateTests {

        @Test
        void shouldReturn201AndCreatedIdentity() throws Exception {
            IdentityCreateRequestDto mockCreateRequestDto = IdentityBuilder.CreateRequest.valid().build();
            Identity mockIdentity = IdentityBuilder.Entity.fromCreateRequest(mockCreateRequestDto).build();
            IdentityResponseDto mockResponseDto = IdentityBuilder.Response.fromEntity(mockIdentity);

            when(identityService.create(any(IdentityCreateRequestDto.class))).thenReturn(mockResponseDto);

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.post().uri("/identity")
                    .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                    .content(objectMapper.writeValueAsString(mockCreateRequestDto));

            assertThat(result).hasStatus(201);
            assertThat(result).bodyJson().isEqualTo(objectMapper.writeValueAsString(mockResponseDto));
        }

        @Test
        void shouldReturn400ForInvalidDto() throws Exception {
            IdentityCreateRequestDto mockCreateRequestDto = IdentityBuilder.CreateRequest.valid().withInvalidCpf().build();

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.post().uri("/identity")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockCreateRequestDto));

            assertThat(result).hasStatus(400);
        }

        @Test
        void shouldReturn409ForDuplicateResource() throws Exception {
            IdentityCreateRequestDto mockCreateRequestDto = IdentityBuilder.CreateRequest.valid().build();
            when(identityService.create(any(IdentityCreateRequestDto.class))).thenThrow(new DuplicateResourceException("CPF já existe"));

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.post().uri("/identity")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockCreateRequestDto));

            assertThat(result).hasStatus(409);
        }

    }

    @Nested
    @DisplayName(value = "Testes de atualização de email")
    class ChangeEmailTests {

        @Test
        void shouldReturn200WhenEmailIsChanged() throws Exception {
            IdentityUpdateEmailRequestDto mockUpdateEmailDto = IdentityBuilder.UpdateEmailRequest.valid().build();
            UUID id = UUID.randomUUID();
            IdentityResponseDto mockResponseDto = IdentityBuilder.Response.valid().withId(id).build();

            when(identityService.changeEmail(eq(id), any(IdentityUpdateEmailRequestDto.class))).thenReturn(mockResponseDto);

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.patch().uri("/identity/{id}/changeEmail", id)
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockUpdateEmailDto));

            assertThat(result).hasStatusOk();
            assertThat(result).bodyJson().isEqualTo(objectMapper.writeValueAsString(mockResponseDto));
        }

    }

    @Nested
    @DisplayName(value = "Testes de atualização de senha")
    class ChangePasswordTests {

        @Test
        void shouldReturn200WhenPasswordIsChanged() throws Exception {
            IdentityUpdatePasswordRequestDto mockUpdatePasswordDto = IdentityBuilder.UpdatePasswordRequest.valid().build();
            UUID id = UUID.randomUUID();
            IdentityResponseDto mockResponseDto = IdentityBuilder.Response.valid().withId(id).build();

            when(identityService.changePassword(eq(id), any(IdentityUpdatePasswordRequestDto.class))).thenReturn(mockResponseDto);

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.patch().uri("/identity/{id}/changePassword", id)
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockUpdatePasswordDto));

            assertThat(result).hasStatusOk();
            assertThat(result).bodyJson().isEqualTo(objectMapper.writeValueAsString(mockResponseDto));
        }

        @Test
        void shouldReturn400WhenOldPasswordIsWrong() throws Exception {
            IdentityUpdatePasswordRequestDto mockUpdatePasswordDto = IdentityBuilder.UpdatePasswordRequest.valid().withWrongOldPassword().build();
            UUID id = UUID.randomUUID();

            when(identityService.changePassword(eq(id), any(IdentityUpdatePasswordRequestDto.class))).thenThrow(new WrongPasswordException("A senha não confere com a atual!"));

            MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.patch().uri("/identity/{id}/changePassword", id)
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockUpdatePasswordDto));

            assertThat(result).hasStatus(400);
        }

    }


    @Nested
    @DisplayName(value = "Testes de mudança de status para ativo")
    class ActivateTests {

        @Test
        void shouldReturn200WhenActivated() {
            testStatusChange("activate");
        }

    }

    @Nested
    @DisplayName(value = "Testes de mudança de status para inativo")
    class DeactivateTests {

        @Test
        void shouldReturn200WhenDeactivated() {
            testStatusChange("deactivate");
        }

    }

    @Nested
    @DisplayName(value = "Testes de mudança de status para suspenso")
    class SuspendTests {

        @Test
        void shouldReturn200WhenSuspended() {
            testStatusChange("suspend");
        }

    }

    private void testStatusChange(String statusPath) {
        UUID id = UUID.randomUUID();
        IdentityResponseDto mockResponseDto = IdentityBuilder.Response.valid().withId(id).build();

        when(identityService.activate(id)).thenReturn(mockResponseDto);
        when(identityService.deactivate(id)).thenReturn(mockResponseDto);
        when(identityService.suspend(id)).thenReturn(mockResponseDto);

        MockMvcTester.MockMvcRequestBuilder result = mockMvcTester.patch().uri("/identity/{id}/{statusPath}", id, statusPath);

        assertThat(result).hasStatusOk();
    }

}