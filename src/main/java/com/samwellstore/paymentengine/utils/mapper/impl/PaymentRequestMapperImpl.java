package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.CustomerDTO;
import com.samwellstore.paymentengine.dto.MerchantDTO;
import com.samwellstore.paymentengine.dto.MerchantWithoutBalanceDTO;
import com.samwellstore.paymentengine.dto.PaymentRequestDTO;
import com.samwellstore.paymentengine.entities.PaymentRequest;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class PaymentRequestMapperImpl implements Mapper<PaymentRequest, PaymentRequestDTO> {
    
    private final ModelMapper modelMapper;
    
    public PaymentRequestMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentRequestDTO mapTo(PaymentRequest paymentRequest) {
        if (paymentRequest == null) {
            return null;
        }
        
        // Manual mapping to avoid conflicts
        PaymentRequestDTO dto = PaymentRequestDTO.builder()
                .id(paymentRequest.getId())
                .amount(paymentRequest.getAmount())
                .reference(paymentRequest.getReference())
                .status(paymentRequest.getStatus())
                .description(paymentRequest.getDescription())
                .customerEmail(paymentRequest.getCustomerEmail())
                .customerName(paymentRequest.getCustomerName())
                .customerPhone(paymentRequest.getCustomerPhone())
                .build();
                
        // Map merchant separately if it exists
        if (paymentRequest.getMerchant() != null) {
            MerchantWithoutBalanceDTO merchantWithoutBalanceDTO = modelMapper.map(paymentRequest.getMerchant(), MerchantWithoutBalanceDTO.class);
            dto.setMerchant(merchantWithoutBalanceDTO);
        }

        
        return dto;
    }

    @Override
    public PaymentRequest mapFrom(PaymentRequestDTO paymentRequestDTO) {
        if (paymentRequestDTO == null) {
            return null;
        }
        
        // Manual mapping to avoid conflicts
        PaymentRequest entity = PaymentRequest.builder()
                .id(paymentRequestDTO.getId())
                .amount(paymentRequestDTO.getAmount())
                .reference(paymentRequestDTO.getReference())
                .status(paymentRequestDTO.getStatus())
                .description(paymentRequestDTO.getDescription())
                .customerEmail(paymentRequestDTO.getCustomerEmail())
                .customerName(paymentRequestDTO.getCustomerName())
                .customerPhone(paymentRequestDTO.getCustomerPhone())
                .build();
                
        // Map merchant separately if it exists
        if (paymentRequestDTO.getMerchant() != null) {
            entity.setMerchant(modelMapper.map(paymentRequestDTO.getMerchant(), com.samwellstore.paymentengine.entities.Merchant.class));
        }

        
        return entity;
    }
}
