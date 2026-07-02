package dev.rynwllngtn.bank.customer.api;

import dev.rynwllngtn.bank.customer.application.dto.CustomerCreateRequestDto;
import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CustomerController implements CustomerAPI {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerResponseDto> findById(UUID id) {
        CustomerResponseDto responseDto = customerService.findById(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<CustomerResponseDto> create(CustomerCreateRequestDto createRequestDto) {
        CustomerResponseDto responseDto = customerService.create(createRequestDto);
        URI uri = ServletUriComponentsBuilder
                  .fromCurrentRequest()
                  .path("/{id}").buildAndExpand(responseDto.id())
                  .toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }
}