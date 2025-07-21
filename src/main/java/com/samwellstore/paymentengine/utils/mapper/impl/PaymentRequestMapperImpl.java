package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.PaymentRequestDTO;
import com.samwellstore.paymentengine.entities.PaymentRequest;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class PaymentRequestMapperImpl implements Mapper<PaymentRequest, PaymentRequestDTO> {
    private ModelMapper modelMapper;
    public PaymentRequestMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentRequestDTO mapTo(PaymentRequest paymentRequest) {
        return modelMapper.map(paymentRequest, PaymentRequestDTO.class);
    }

    @Override
    public PaymentRequest mapFrom(PaymentRequestDTO paymentRequestDTO ){
        return modelMapper.map(paymentRequestDTO, PaymentRequest.class);
    }

}
