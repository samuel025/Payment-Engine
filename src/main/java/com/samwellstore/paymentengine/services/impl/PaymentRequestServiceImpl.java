package com.samwellstore.paymentengine.services.impl;

import java.util.UUID;

import com.samwellstore.paymentengine.Repositories.CustomerRepository;
import com.samwellstore.paymentengine.entities.Customer;
import com.samwellstore.paymentengine.enums.Roles;
import com.samwellstore.paymentengine.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    final CustomerRepository customerRepository;

    @Autowired
    private Mapper<PaymentRequest, PaymentRequestDTO> paymentMapper;

    public PaymentRequestServiceImpl(PaymentRequestRepository paymentRequestRepository, MerchantRepository merchantRepository, CustomerRepository customerRepository) {
        this.paymentRequestRepository = paymentRequestRepository;
        this.merchantRepository = merchantRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public PaymentRequestDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Fix: Check if principal is actually a UserPrincipal before casting
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            if (userPrincipal.getUserType().equals(Roles.MERCHANT)) {
                throw new RuntimeException("Only customers can create payment requests");
            }

            // Rest of merchant validation code
            if (paymentRequestDTO.getMerchant() == null || paymentRequestDTO.getMerchant().getId() == null) {
                throw new EntityNotFoundException("Merchant ID is required");
            }

            PaymentRequest paymentRequestEntity = paymentMapper.mapFrom(paymentRequestDTO);
            Merchant merchant = merchantRepository.findById(paymentRequestEntity.getMerchant().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Merchant not found"));
            paymentRequestEntity.setMerchant(merchant);

            // If authenticated, use the customer from authentication
            Customer customer = customerRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            paymentRequestEntity.setCustomer(customer);

            paymentRequestEntity.setReference("PAY_" + UUID.randomUUID().toString().substring(0, 8));
            paymentRequestEntity.setStatus(PaymentStatus.PENDING);
            paymentRequestEntity.setAmount(paymentRequestDTO.getAmount());
            PaymentRequest savedPaymentRequest = paymentRequestRepository.save(paymentRequestEntity);
            return paymentMapper.mapTo(savedPaymentRequest);
        } else {
            // Handle unauthenticated or non-UserPrincipal case
            // This could be a public API endpoint or a different authentication method
            if (paymentRequestDTO.getMerchant() == null || paymentRequestDTO.getMerchant().getId() == null) {
                throw new EntityNotFoundException("Merchant ID is required");
            }

            // Customer information is required for non-authenticated requests
            if (paymentRequestDTO.getCustomerEmail() == null || paymentRequestDTO.getCustomerName() == null || paymentRequestDTO.getCustomerPhone() == null)  {
                throw new IllegalArgumentException("Customer information is required");
            }

            PaymentRequest paymentRequestEntity = paymentMapper.mapFrom(paymentRequestDTO);
            Merchant merchant = merchantRepository.findById(paymentRequestEntity.getMerchant().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Merchant not found"));
            paymentRequestEntity.setMerchant(merchant);

            // For non-authenticated requests, use the provided customer details
            paymentRequestEntity.setCustomerName(paymentRequestDTO.getCustomerName());
            paymentRequestEntity.setCustomerEmail(paymentRequestDTO.getCustomerEmail());
            paymentRequestEntity.setCustomerPhone(paymentRequestDTO.getCustomerPhone());

            paymentRequestEntity.setReference("PAY_" + UUID.randomUUID().toString().substring(0, 8));
            paymentRequestEntity.setStatus(PaymentStatus.PENDING);
            paymentRequestEntity.setAmount(paymentRequestDTO.getAmount());
            PaymentRequest savedPaymentRequest = paymentRequestRepository.save(paymentRequestEntity);
            return paymentMapper.mapTo(savedPaymentRequest);
        }
    }

    @Override
    public PaymentRequestDTO getPaymentByReference(String reference) {
        PaymentRequest paymentRequest = paymentRequestRepository.findByReference(reference)
            .orElseThrow(() -> new EntityNotFoundException("Payment not found with reference: " + reference));
    return paymentMapper.mapTo(paymentRequest);
    }
}
