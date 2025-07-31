package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.AuthenticationDTOs.SignUpRequestDTO;
import com.samwellstore.paymentengine.entities.Merchant;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class SignupRequestToMerchantImpl implements Mapper<Merchant, SignUpRequestDTO> {

    final ModelMapper modelMapper;

    public SignupRequestToMerchantImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SignUpRequestDTO mapTo(Merchant merchant) {
        return modelMapper.map(merchant, SignUpRequestDTO.class);
    }

    @Override
    public Merchant mapFrom(SignUpRequestDTO signUpRequest) {
        return modelMapper.map(signUpRequest, Merchant.class);
    }

}
