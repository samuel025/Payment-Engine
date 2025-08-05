package com.samwellstore.paymentengine.utils.mapper.impl.newmappers;

import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantWithoutBalanceDTO;
import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.entities.Payment;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class PaymentRequestMapperImpl implements Mapper<Payment, PaymentDTO> {
    
    private final ModelMapper modelMapper;
    
    public PaymentRequestMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentDTO mapTo(Payment payment) {
        if (payment == null) {
            return null;
        }
        
        // Manual mapping to avoid conflicts
        PaymentDTO dto = PaymentDTO.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .reference(payment.getReference())
                .status(payment.getStatus())
                .description(payment.getDescription())
                .customerEmail(payment.getCustomerEmail())
                .customerName(payment.getCustomerName())
                .customerPhone(payment.getCustomerPhone())
                .build();
                
        // Map merchant separately if it exists
        if (payment.getMerchant() != null) {
            MerchantWithoutBalanceDTO merchantWithoutBalanceDTO = modelMapper.map(payment.getMerchant(), MerchantWithoutBalanceDTO.class);
            dto.setMerchant(merchantWithoutBalanceDTO);
        }

        
        return dto;
    }

    @Override
    public Payment mapFrom(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }
        
        // Manual mapping to avoid conflicts
        Payment entity = Payment.builder()
                .id(paymentDTO.getId())
                .amount(paymentDTO.getAmount())
                .reference(paymentDTO.getReference())
                .status(paymentDTO.getStatus())
                .description(paymentDTO.getDescription())
                .customerEmail(paymentDTO.getCustomerEmail())
                .customerName(paymentDTO.getCustomerName())
                .customerPhone(paymentDTO.getCustomerPhone())
                .build();
                
        // Map merchant separately if it exists
        if (paymentDTO.getMerchant() != null) {
            entity.setMerchant(modelMapper.map(paymentDTO.getMerchant(), User.class));
        }

        
        return entity;
    }
}
