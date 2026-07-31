package dev.rynwllngtn.bank.customer.api.http;

import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CustomerResponseDto> completeRegistration(UUID id) {
        CustomerResponseDto responseDto = customerService.completeRegistration(id);
        return ResponseEntity.ok().body(responseDto);
    }

}