package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.CustomerDTO;
import com.samwellstore.paymentengine.entities.Customer;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapperImpl implements Mapper<Customer, CustomerDTO> {

    final ModelMapper modelMapper;

    public CustomerMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO mapTo(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public Customer mapFrom(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}
