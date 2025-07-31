package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.entities.Merchant;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MerchantMapperImpl implements Mapper<Merchant, MerchantDTO> {
    private ModelMapper modelMapper;
    public MerchantMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MerchantDTO mapTo(Merchant merchant) {
        return modelMapper.map(merchant, MerchantDTO.class);
    }

    @Override
    public Merchant mapFrom(MerchantDTO merchantDTO) {
        return modelMapper.map(merchantDTO, Merchant.class);
    }
}
