package com.samwellstore.paymentengine.utils.mapper.impl.newmappers;

import com.samwellstore.paymentengine.dto.SignUpDTOs.CustomerSignupDTO;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerSignUpMapper implements Mapper<User, CustomerSignupDTO> {

    final ModelMapper modelMapper;

    public CustomerSignUpMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerSignupDTO mapTo(User user) {
        if (user == null) {
            return null;
        }

        return CustomerSignupDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .address(user.getAddress())
                .build();
    }

    @Override
    public User mapFrom(CustomerSignupDTO customerSignupDTO) {
        if (customerSignupDTO == null) {
            return null;
        }

        return User.builder()
                .email(customerSignupDTO.getEmail())
                .password(customerSignupDTO.getPassword())
                .firstName(customerSignupDTO.getFirstName())
                .lastName(customerSignupDTO.getLastName())
                .phoneNumber(customerSignupDTO.getPhoneNumber())
                .address(customerSignupDTO.getAddress())
                .role(customerSignupDTO.getRole())
                .build();

    }
}

