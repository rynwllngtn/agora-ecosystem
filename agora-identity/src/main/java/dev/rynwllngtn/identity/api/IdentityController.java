package dev.rynwllngtn.identity.api;

import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdateEmailRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdatePasswordRequestDto;
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

    @Override
    public ResponseEntity<IdentityResponseDto> changePassword(UUID id,
                                                              IdentityUpdatePasswordRequestDto updateRequestDto) {
        IdentityResponseDto responseDto = identityService.changePassword(id, updateRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<IdentityResponseDto> changeEmail(UUID id,
                                                           IdentityUpdateEmailRequestDto updateRequestDto) {
        IdentityResponseDto responseDto = identityService.changeEmail(id, updateRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<IdentityResponseDto> activate(UUID id) {
        IdentityResponseDto responseDto = identityService.activate(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<IdentityResponseDto> deactivate(UUID id) {
        IdentityResponseDto responseDto = identityService.deactivate(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<IdentityResponseDto> suspend(UUID id) {
        IdentityResponseDto responseDto = identityService.suspend(id);
        return ResponseEntity.ok().body(responseDto);
    }

}