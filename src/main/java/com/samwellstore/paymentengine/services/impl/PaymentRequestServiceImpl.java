package com.samwellstore.paymentengine.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samwellstore.paymentengine.Repositories.MerchantRepository;
import com.samwellstore.paymentengine.Repositories.PaymentRequestRepository;
import com.samwellstore.paymentengine.dto.PaymentRequestDTO;
import com.samwellstore.paymentengine.entities.Merchant;
import com.samwellstore.paymentengine.entities.PaymentRequest;
import com.samwellstore.paymentengine.enums.PaymentStatus;
import com.samwellstore.paymentengine.services.PaymentRequestService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {
    final PaymentRequestRepository paymentRequestRepository;
    final MerchantRepository merchantRepository;

    @Autowired
    private Mapper<PaymentRequest, PaymentRequestDTO> paymentMapper;

    public PaymentRequestServiceImpl(PaymentRequestRepository paymentRequestRepository, MerchantRepository merchantRepository) {
        this.paymentRequestRepository = paymentRequestRepository;
        this.merchantRepository = merchantRepository;
    }

    @Override
    public PaymentRequestDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
        PaymentRequest paymentRequestEntity = paymentMapper.mapFrom(paymentRequestDTO);
        Merchant merchant = merchantRepository.findById(paymentRequestEntity.getMerchant().getId())
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found"));
        paymentRequestEntity.setMerchant(merchant);
        paymentRequestEntity.setReference("PAY_" + UUID.randomUUID().toString().substring(0, 8));
        paymentRequestEntity.setStatus(PaymentStatus.PENDING);
        PaymentRequest savedPaymentRequest = paymentRequestRepository.save(paymentRequestEntity);
        return paymentMapper.mapTo(savedPaymentRequest);
    }

    @Override
    public PaymentRequestDTO getPaymentByReference(String reference) {
        PaymentRequest paymentRequest = paymentRequestRepository.findByReference(reference)
            .orElseThrow(() -> new EntityNotFoundException("Payment not found with reference: " + reference));
    return paymentMapper.mapTo(paymentRequest);
    }
}
