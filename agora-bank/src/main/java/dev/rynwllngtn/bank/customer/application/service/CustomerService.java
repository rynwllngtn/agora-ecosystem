package dev.rynwllngtn.bank.customer.application.service;

import dev.rynwllngtn.bank.customer.application.dto.CustomerCreateRequestDto;
import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;

import java.util.UUID;

public interface CustomerService {

    CustomerResponseDto findById(UUID id);

    CustomerResponseDto create(CustomerCreateRequestDto createRequestDto);

}