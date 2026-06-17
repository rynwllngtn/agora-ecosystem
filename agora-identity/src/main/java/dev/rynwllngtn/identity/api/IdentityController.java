package dev.rynwllngtn.identity.api;

import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @Override
    public ResponseEntity<IdentityResponseDto> create(IdentityCreateRequestDto createRequestDto) {
        IdentityResponseDto responseDto = identityService.create(createRequestDto);
        URI uri = ServletUriComponentsBuilder
                  .fromCurrentRequest()
                  .path("/{id}").buildAndExpand(responseDto.id())
                  .toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

}