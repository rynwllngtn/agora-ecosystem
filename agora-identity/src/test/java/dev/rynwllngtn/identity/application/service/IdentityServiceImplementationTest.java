package dev.rynwllngtn.identity.application.service;

import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdateEmailRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdatePasswordRequestDto;
import dev.rynwllngtn.identity.application.exception.DuplicateResourceException;
import dev.rynwllngtn.identity.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.identity.application.exception.WrongPasswordException;
import dev.rynwllngtn.identity.application.mapper.IdentityMapper;
import dev.rynwllngtn.identity.builder.IdentityBuilder;
import dev.rynwllngtn.identity.domain.Identity;
import dev.rynwllngtn.identity.domain.IdentityRepository;
import dev.rynwllngtn.identity.domain.IdentityStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IdentityServiceImplementationTest {

    @Mock
    private IdentityRepository identityRepository;

    @Mock
    private IdentityMapper identityMapper;

    @InjectMocks
    private IdentityServiceImplementation identityService;

    @Nested
    @DisplayName(value = "Testes de busca por ID")
    class FindByIdTests {

        @Test
        void shouldReturnIdentityResponseDto() {
            Identity mockIdentity = IdentityBuilder.Entity.valid().build();
            IdentityResponseDto mockResponseDto = IdentityBuilder.Response.fromEntity(mockIdentity);

            UUID id = mockIdentity.getId();
            when(identityRepository.findById(id)).thenReturn(Optional.of(mockIdentity));
            when(identityMapper.toResponseDto(mockIdentity)).thenReturn(mockResponseDto);

            IdentityResponseDto result = identityService.findById(id);

            assertNotNull(result);
            assertEquals(mockResponseDto.id(), result.id());
            assertEquals(mockResponseDto.email(), result.email());
            assertEquals(mockResponseDto.status(), result.status());

            verify(identityRepository).findById(id);
            verify(identityMapper).toResponseDto(mockIdentity);
        }

        @Test
        void shouldThrowResourceNotFoundException_() {
            UUID nonExistentId = UUID.randomUUID();
            when(identityRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                identityService.findById(nonExistentId);
            });

            verify(identityRepository).findById(nonExistentId);
        }

    }

    @Nested
    @DisplayName(value = "Testes de criação")
    class CreateTests {

        @Test
        void shouldCreateIdentityAndSave() {
            IdentityCreateRequestDto mockCreateRequestDto = IdentityBuilder.CreateRequest.valid().build();
            Identity mockIdentity = IdentityBuilder.Entity.fromCreateRequest(mockCreateRequestDto).build();
            IdentityResponseDto mockResponseDto = IdentityBuilder.Response.fromEntity(mockIdentity);

            when(identityRepository.save(mockIdentity)).thenReturn(mockIdentity);
            when(identityMapper.toEntity(mockCreateRequestDto)).thenReturn(mockIdentity);
            when(identityMapper.toResponseDto(mockIdentity)).thenReturn(mockResponseDto);

            IdentityResponseDto result = identityService.create(mockCreateRequestDto);

            assertNotNull(result);
            assertEquals(mockResponseDto.id(), result.id());
            assertEquals(mockResponseDto.email(), result.email());
            assertEquals(mockResponseDto.status(), result.status());

            verify(identityRepository).save(mockIdentity);
            verify(identityMapper).toEntity(mockCreateRequestDto);
            verify(identityMapper).toResponseDto(mockIdentity);
        }

        @Test
        void shouldThrowForDuplicateCpf() {
            IdentityCreateRequestDto mockCreateRequestDto = IdentityBuilder.CreateRequest.valid().build();
            when(identityRepository.existsByCpf(mockCreateRequestDto.cpf())).thenReturn(true);

            assertThrows(DuplicateResourceException.class,
                    () -> identityService.create(mockCreateRequestDto)
            );

            verify(identityRepository).existsByCpf(mockCreateRequestDto.cpf());
        }

        @Test
        void shouldThrowForDuplicateEmail() {
            IdentityCreateRequestDto mockCreateRequestDto = IdentityBuilder.CreateRequest.valid().build();
            when(identityRepository.existsByEmail(mockCreateRequestDto.email())).thenReturn(true);

            assertThrows(DuplicateResourceException.class,
                    () -> identityService.create(mockCreateRequestDto)
            );

            verify(identityRepository).existsByEmail(mockCreateRequestDto.email());
        }

    }

    @Nested
    @DisplayName(value = "Testes de atualização de email")
    class ChangeEmailTests {

        @Test
        void shouldUpdateEmailAndSave() {
            Identity mockIdentity = IdentityBuilder.Entity.valid().build();
            IdentityUpdateEmailRequestDto mockUpdateRequestDto = IdentityBuilder.UpdateEmailRequest.valid().build();
            UUID id = mockIdentity.getId();

            when(identityRepository.findById(id)).thenReturn(Optional.of(mockIdentity));
            when(identityRepository.existsByEmail(mockUpdateRequestDto.newEmail())).thenReturn(false);
            when(identityRepository.save(any(Identity.class))).thenReturn(mockIdentity);

            identityService.changeEmail(id, mockUpdateRequestDto);

            assertEquals(IdentityBuilder.updateEmail, mockIdentity.getEmail());

            verify(identityRepository).findById(id);
            verify(identityRepository).existsByEmail(mockUpdateRequestDto.newEmail());
            verify(identityRepository).save(mockIdentity);
        }

        @Test
        void shouldThrowWhenEmailIsDuplicate() {
            IdentityUpdateEmailRequestDto mockUpdateRequestDto = IdentityBuilder.UpdateEmailRequest.valid().build();

            when(identityRepository.existsByEmail(mockUpdateRequestDto.newEmail())).thenReturn(true);

            assertThrows(DuplicateResourceException.class,
                    () -> identityService.changeEmail(UUID.randomUUID(), mockUpdateRequestDto)
            );

            verify(identityRepository).existsByEmail(mockUpdateRequestDto.newEmail());
        }

        @Test
        void shouldThrowWhenPasswordIsWrong() {
            Identity mockIdentity = IdentityBuilder.Entity.valid().build();
            IdentityUpdateEmailRequestDto mockUpdateRequestDto = IdentityBuilder.UpdateEmailRequest.valid().withWrongPassword().build();
            UUID id = mockIdentity.getId();

            when(identityRepository.findById(id)).thenReturn(Optional.of(mockIdentity));

            assertThrows(WrongPasswordException.class,
                    () -> identityService.changeEmail(id, mockUpdateRequestDto)
            );

            verify(identityRepository, never()).save(any(Identity.class));
        }

    }

    @Nested
    @DisplayName(value = "Testes de atualização de senha")
    class ChangePasswordTests {

        @Test
        void shouldUpdatePasswordAndSave() {
            Identity mockIdentity = IdentityBuilder.Entity.valid().build();
            IdentityUpdatePasswordRequestDto mockUpdateRequestDto = IdentityBuilder.UpdatePasswordRequest.valid().build();
            UUID id = mockIdentity.getId();

            when(identityRepository.findById(id)).thenReturn(Optional.of(mockIdentity));
            when(identityRepository.save(any(Identity.class))).thenReturn(mockIdentity);

            identityService.changePassword(id, mockUpdateRequestDto);

            IO.println(mockIdentity.toString());

            assertEquals(IdentityBuilder.updatePassword, mockIdentity.getPassword());

            verify(identityRepository).findById(id);
            verify(identityRepository).save(mockIdentity);
        }

        @Test
        void shouldThrowWhenOldPasswordIsWrong() {
            Identity mockIdentity = IdentityBuilder.Entity.valid().build();
            IdentityUpdatePasswordRequestDto mockUpdateRequestDto = IdentityBuilder.UpdatePasswordRequest.valid().withWrongOldPassword().build();
            UUID id = mockIdentity.getId();

            when(identityRepository.findById(id)).thenReturn(Optional.of(mockIdentity));

            assertThrows(WrongPasswordException.class, () -> {
                identityService.changePassword(id, mockUpdateRequestDto);
            });

            verify(identityRepository, never()).save(any(Identity.class));
        }

        @Test
        void shouldThrowWhenNewPasswordConfirmationFails() {
            IdentityUpdatePasswordRequestDto mockUpdateRequestDto = IdentityBuilder.UpdatePasswordRequest.valid().withPasswordsNotMatching().build();
            UUID id = UUID.randomUUID();

            assertThrows(WrongPasswordException.class, () -> {
                identityService.changePassword(id, mockUpdateRequestDto);
            });

            verify(identityRepository, never()).findById(any(UUID.class));
        }

    }

    @Nested
    @DisplayName(value = "Testes de mudança de status para ativo")
    class ActivateTests {

        @Test
        void shouldActivateAndSave() {
            Identity mockIdentity = IdentityBuilder.Entity.valid().build();
            mockIdentity.deactivate();
            UUID id = mockIdentity.getId();

            when(identityRepository.findById(id)).thenReturn(Optional.of(mockIdentity));
            when(identityRepository.save(mockIdentity)).thenReturn(mockIdentity);

            identityService.activate(id);

            assertEquals(IdentityStatus.ACTIVE, mockIdentity.getStatus());

            verify(identityRepository).findById(id);
            verify(identityRepository).save(mockIdentity);
        }

    }

    @Nested
    @DisplayName(value = "Testes de mudança de status para inativo")
    class DeactivateTests {

        @Test
        void shouldDeactivateAndSave() {
            Identity mockIdentity = IdentityBuilder.Entity.valid().build();
            UUID id = mockIdentity.getId();

            when(identityRepository.findById(id)).thenReturn(Optional.of(mockIdentity));
            when(identityRepository.save(mockIdentity)).thenReturn(mockIdentity);

            identityService.deactivate(id);

            assertEquals(IdentityStatus.DEACTIVATED, mockIdentity.getStatus());

            verify(identityRepository).findById(id);
            verify(identityRepository).save(mockIdentity);
        }

    }

    @Nested
    @DisplayName(value = "Testes de mudança de status para suspenso")
    class SuspendTests {

        @Test
        void shouldSuspendAndSave() {
            Identity mockIdentity = IdentityBuilder.Entity.valid().build();
            UUID id = mockIdentity.getId();

            when(identityRepository.findById(id)).thenReturn(Optional.of(mockIdentity));
            when(identityRepository.save(mockIdentity)).thenReturn(mockIdentity);

            identityService.suspend(id);

            assertEquals(IdentityStatus.SUSPENDED, mockIdentity.getStatus());

            verify(identityRepository).findById(id);
            verify(identityRepository).save(mockIdentity);
        }

    }

}
