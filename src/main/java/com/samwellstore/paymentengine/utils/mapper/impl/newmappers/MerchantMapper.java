package com.samwellstore.paymentengine.utils.mapper.impl.newmappers;

import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MerchantMapper implements Mapper<User, MerchantDTO> {
    private ModelMapper modelMapper;
    public MerchantMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MerchantDTO mapTo(User user) {
        return modelMapper.map(user, MerchantDTO.class);
    }

    @Override
    public User mapFrom(MerchantDTO merchantDTO) {
        return modelMapper.map(merchantDTO, User.class);
    }
}
