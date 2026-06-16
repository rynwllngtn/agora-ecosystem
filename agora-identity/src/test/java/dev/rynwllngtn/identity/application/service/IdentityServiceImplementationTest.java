package dev.rynwllngtn.identity.application.service;

import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.identity.application.mapper.IdentityMapper;
import dev.rynwllngtn.identity.domain.Identity;
import dev.rynwllngtn.identity.domain.IdentityRepository;
import dev.rynwllngtn.identity.domain.IdentityStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IdentityServiceImplementationTest {

    @Mock
    private IdentityRepository identityRepository;

    @Mock
    private IdentityMapper identityMapper;

    @InjectMocks
    private IdentityServiceImplementation identityService;

    @Test
    void shouldReturnIdentityResponseDto() {
        Identity mockIdentity = new Identity("11122233344", "password", "email@email.com");
        UUID id = mockIdentity.getId();
        IdentityResponseDto mockResponseDto = new IdentityResponseDto(id, "email@email.com", IdentityStatus.ACTIVE);

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