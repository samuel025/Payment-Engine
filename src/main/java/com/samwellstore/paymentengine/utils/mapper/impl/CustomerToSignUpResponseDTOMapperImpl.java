package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.CustomerSignUpResponseDTO;
import com.samwellstore.paymentengine.entities.Customer;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;



@Component
public class CustomerToSignUpResponseDTOMapperImpl implements Mapper<Customer, CustomerSignUpResponseDTO> {
    final ModelMapper modelMapper;
    public CustomerToSignUpResponseDTOMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerSignUpResponseDTO mapTo(Customer merchant) {
        return modelMapper.map(merchant, CustomerSignUpResponseDTO.class);
    }

    @Override
    public Customer mapFrom(CustomerSignUpResponseDTO customerSignUpResponseDTO) {
        return modelMapper.map(customerSignUpResponseDTO, Customer.class);
    }
}