package dev.rynwllngtn.identity.application.mapper;

import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.domain.Identity;
import dev.rynwllngtn.identity.domain.IdentityStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IdentityMapperTest {

    private final IdentityMapper identityMapper = new IdentityMapper();

    @Test
    void shouldMapIdentityToIdentityResponseDto() {
        Identity identity = new Identity("11122233344", "password", "email@email.com");
        identity.deactivate();

        IdentityResponseDto responseDto = identityMapper.toResponseDto(identity);

        assertNotNull(responseDto);
        assertEquals(identity.getId(), responseDto.id());
        assertEquals(identity.getEmail(), responseDto.email());
        assertEquals(IdentityStatus.DEACTIVATED, responseDto.status());
    }

}