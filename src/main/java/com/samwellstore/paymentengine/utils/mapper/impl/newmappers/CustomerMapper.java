package com.samwellstore.paymentengine.utils.mapper.impl.newmappers;

import com.samwellstore.paymentengine.dto.CustomerDTOs.CustomerDTO;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper implements Mapper<User, CustomerDTO> {
    final ModelMapper modelMapper;
    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public CustomerDTO mapTo(User user) {
        return modelMapper.map(user, CustomerDTO.class);
    }

    @Override
    public User mapFrom(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, User.class);
    }
}
