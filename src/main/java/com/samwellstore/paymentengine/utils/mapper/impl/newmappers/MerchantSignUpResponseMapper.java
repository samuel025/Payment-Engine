package com.samwellstore.paymentengine.utils.mapper.impl.newmappers;

import com.samwellstore.paymentengine.dto.SignUpDTOs.MerchantSignUpResponseDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.MerchantSignUpDTO;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class MerchantSignUpResponseMapper implements Mapper<MerchantSignUpResponseDTO, MerchantSignUpDTO> {

    final ModelMapper modelMapper;

    public MerchantSignUpResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MerchantSignUpDTO mapTo(MerchantSignUpResponseDTO merchantSignUpResponseDTO) {
        return modelMapper.map(merchantSignUpResponseDTO, MerchantSignUpDTO.class);
    }

    @Override
    public MerchantSignUpResponseDTO mapFrom(MerchantSignUpDTO merchantSignUpDTO) {
        return modelMapper.map(merchantSignUpDTO, MerchantSignUpResponseDTO.class);
    }
}
