package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantSignUpResponseDTO;
import com.samwellstore.paymentengine.entities.Merchant;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class MerchantToSignUpResponseDTOMapperImpl implements Mapper<Merchant, MerchantSignUpResponseDTO> {
    final ModelMapper modelMapper;
    public MerchantToSignUpResponseDTOMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MerchantSignUpResponseDTO mapTo(Merchant merchant) {
        return modelMapper.map(merchant, MerchantSignUpResponseDTO.class);
    }

    @Override
    public Merchant mapFrom(MerchantSignUpResponseDTO merchantSignUpResponseDTO) {
        return modelMapper.map(merchantSignUpResponseDTO, Merchant.class);
    }
}
