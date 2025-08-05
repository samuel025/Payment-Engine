package com.samwellstore.paymentengine.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import com.samwellstore.paymentengine.Repositories.UserRepository;
import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import com.samwellstore.paymentengine.entities.*;
import com.samwellstore.paymentengine.enums.Roles;
import com.samwellstore.paymentengine.security.UserPrincipal;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.samwellstore.paymentengine.Repositories.PaymentRepository;
import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.enums.PaymentStatus;
import com.samwellstore.paymentengine.services.PaymentService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaymentServiceImpl implements PaymentService {
    final PaymentRepository paymentRepository;
    final UserRepository userRepository;
    final Mapper<Payment, PaymentDTO> paymentMapper;
    final Mapper<Transaction, TransactionDTO> transactionMapper;


    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository, Mapper<Payment, PaymentDTO> paymentMapper, Mapper<Transaction, TransactionDTO> transactionMapper) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.paymentMapper = paymentMapper;
        this.transactionMapper = transactionMapper;
    }


    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO, UserPrincipal userPrincipal, Long merchantId) {

        Payment paymentEntity = paymentMapper.mapFrom(paymentDTO);
        User merchant = userRepository.findMerchantById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found"));
        paymentEntity.setMerchant(merchant);

        User customer = userRepository.findCustomerById(userPrincipal.getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        paymentEntity.setCustomer(customer);

        paymentEntity.setReference("PAY_" + UUID.randomUUID().toString().substring(0, 8));
        paymentEntity.setStatus(PaymentStatus.PENDING);
        paymentEntity.setAmount(paymentDTO.getAmount());
        Payment savedPayment = paymentRepository.save(paymentEntity);
        return paymentMapper.mapTo(savedPayment);
    }

    @Override
    public PaymentDTO createAnonymousPayment(PaymentDTO paymentDTO, Long merchantId) {
        if (paymentDTO.getCustomerEmail() == null || paymentDTO.getCustomerName() == null || paymentDTO.getCustomerPhone() == null)  {
            throw new IllegalArgumentException("Customer information is required");
        }

        Payment paymentEntity = paymentMapper.mapFrom(paymentDTO);
        User merchant = userRepository.findMerchantById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found"));
        paymentEntity.setMerchant(merchant);

        paymentEntity.setCustomerName(paymentDTO.getCustomerName());
        paymentEntity.setCustomerEmail(paymentDTO.getCustomerEmail());
        paymentEntity.setCustomerPhone(paymentDTO.getCustomerPhone());

        paymentEntity.setReference("PAY_" + UUID.randomUUID().toString().substring(0, 8));
        paymentEntity.setStatus(PaymentStatus.PENDING);
        paymentEntity.setAmount(paymentDTO.getAmount());
        Payment savedPayment = paymentRepository.save(paymentEntity);
        return paymentMapper.mapTo(savedPayment);
    }


    @Override
    public List<PaymentDTO> getPayments(UserPrincipal userPrincipal) {
        if(userPrincipal.getRole().equals(Roles.CUSTOMER)) {
            List<Payment> payments = paymentRepository.findByCustomerId(userPrincipal.getId());
            return payments.stream().map(paymentMapper::mapTo).collect(Collectors.toList());
        } else {
            throw new AuthenticationException("Customer authentication required") {
            };
        }
    }

    @Override
    public List<TransactionDTO> customerGetTransactionsByPaymentRequest(String paymentReference, UserPrincipal userPrincipal) {
        Payment payment = paymentRepository.findByReference(paymentReference)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with reference: " + paymentReference));
        if (payment.getCustomer() != null && payment.getCustomer().getId().equals(userPrincipal.getId())) {
            List<Transaction> transactions = payment.getTransactions();
            return transactions.stream().map(transactionMapper::mapTo).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("No transactions found for payment request with ID: " + paymentReference);
        }
    }
}
