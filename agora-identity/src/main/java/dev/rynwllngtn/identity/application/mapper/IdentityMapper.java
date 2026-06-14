package dev.rynwllngtn.identity.application.mapper;

import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.domain.Identity;
import org.springframework.stereotype.Component;

@Component
public class IdentityMapper {

    public IdentityResponseDto toResponseDto(Identity identity) {
        return new IdentityResponseDto(identity.getId(),
                                       identity.getEmail(),
                                       identity.getStatus());
    }

}