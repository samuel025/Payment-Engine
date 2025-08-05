package com.samwellstore.paymentengine.utils.mapper.impl.newmappers;

import com.samwellstore.paymentengine.dto.SignUpDTOs.MerchantSignUpDTO;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MerchantSignupMapper implements Mapper<User, MerchantSignUpDTO> {
    final ModelMapper modelMapper;

    public MerchantSignupMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MerchantSignUpDTO mapTo(User user) {
        return modelMapper.map(user, MerchantSignUpDTO.class);
    }

    @Override
    public User mapFrom(MerchantSignUpDTO merchantSignUpDTO) {
        return modelMapper.map(merchantSignUpDTO, User.class);
    }
}

