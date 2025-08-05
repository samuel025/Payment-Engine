package com.samwellstore.paymentengine.utils.mapper.impl.newmappers;

import com.samwellstore.paymentengine.dto.SignUpDTOs.CustomerSignUpResponseDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.CustomerSignupDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.MerchantSignUpDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.MerchantSignUpResponseDTO;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerSignUpResponseMapper implements Mapper<CustomerSignUpResponseDTO, CustomerSignupDTO> {

    final ModelMapper modelMapper;

    public CustomerSignUpResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerSignupDTO mapTo(CustomerSignUpResponseDTO customerSignUpResponseDTO) {
        return modelMapper.map(customerSignUpResponseDTO, CustomerSignupDTO.class);
    }

    @Override
    public CustomerSignUpResponseDTO mapFrom(CustomerSignupDTO customerSignupDTO) {
        return modelMapper.map(customerSignupDTO, CustomerSignUpResponseDTO.class);
    }
}
