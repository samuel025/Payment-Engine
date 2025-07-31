package com.samwellstore.paymentengine.utils.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.samwellstore.paymentengine.dto.AuthenticationDTOs.SignUpRequestDTO;
import com.samwellstore.paymentengine.entities.Customer;
import com.samwellstore.paymentengine.utils.mapper.Mapper;


@Component
public class signUpRequestToCustomerMapperImpl implements Mapper<Customer, SignUpRequestDTO> {

    final ModelMapper modelMapper;

    public signUpRequestToCustomerMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SignUpRequestDTO mapTo(Customer customer) {
        return modelMapper.map(customer, SignUpRequestDTO.class);
    }

    @Override
    public Customer mapFrom(SignUpRequestDTO signUpRequestDTO) {
        return modelMapper.map(signUpRequestDTO, Customer.class);
    }
}
