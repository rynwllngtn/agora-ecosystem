package dev.rynwllngtn.identity.api;

import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class IdentityController implements IdentityAPI {

    private final IdentityService identityService;

    @Override
    public ResponseEntity<IdentityResponseDto> findById(UUID id) {
        IdentityResponseDto responseDto = identityService.findById(id);
        return ResponseEntity.ok().body(responseDto);
    }

}