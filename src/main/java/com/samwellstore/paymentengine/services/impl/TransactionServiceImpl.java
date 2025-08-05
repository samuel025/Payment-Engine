package com.samwellstore.paymentengine.services.impl;

import com.samwellstore.paymentengine.Repositories.PaymentRepository;
import com.samwellstore.paymentengine.Repositories.TransactionRepository;
import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import com.samwellstore.paymentengine.entities.Payment;
import com.samwellstore.paymentengine.entities.Transaction;
import com.samwellstore.paymentengine.enums.PaymentStatus;
import com.samwellstore.paymentengine.enums.TransactionStatus;
import com.samwellstore.paymentengine.exceptions.PaymentException;
import com.samwellstore.paymentengine.security.UserPrincipal;
import com.samwellstore.paymentengine.services.MerchantService;
import com.samwellstore.paymentengine.services.TransactionService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private Mapper<Transaction, TransactionDTO> transactionMapper;

    final MerchantService merchantService;
    final TransactionRepository transactionRepository;
    final PaymentRepository paymentRepository;

    public TransactionServiceImpl(MerchantService merchantService, TransactionRepository transactionRepository, PaymentRepository paymentRepository) {
        this.merchantService = merchantService;
        this.transactionRepository = transactionRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public TransactionDTO processCustomerTransaction(TransactionDTO transactionDTO, String ref, UserPrincipal userPrincipal) {
        Transaction transactionEntity = transactionMapper.mapFrom(transactionDTO);
        Payment paymentEntity = paymentRepository.findByReference(ref).orElseThrow(() -> new EntityNotFoundException("Payment request not found"));
        if(paymentEntity.getStatus() != PaymentStatus.PENDING){
            throw new PaymentException("Payment request has been processed");
        }
        boolean hasSuccessfulTransaction = paymentEntity.getTransactions().stream().anyMatch(t -> t.getStatus().equals(TransactionStatus.SUCCESS));
        if(hasSuccessfulTransaction){
            throw new PaymentException("Payment already has successful transaction");
        }
        transactionEntity.setPayment(paymentEntity);
        transactionEntity.setTransactionReference("TXN_" + UUID.randomUUID().toString().substring(0, 8));
        boolean isSuccessful = Math.random() > 0.5;
        if(paymentEntity.getCustomer().getId().equals(userPrincipal.getId())) {
            if (isSuccessful) {
                transactionEntity.setStatus(TransactionStatus.SUCCESS);
                paymentEntity.setStatus(PaymentStatus.PAID);
                merchantService.creditWallet(paymentEntity.getMerchant().getId(),
                        paymentEntity.getAmount());
            } else {
                transactionEntity.setStatus(TransactionStatus.FAILED);
                long failedAttempts = paymentEntity.getTransactions().stream()
                        .filter(t -> t.getStatus().equals(TransactionStatus.FAILED))
                        .count() + 1;
                if (failedAttempts >= 3) {
                    paymentEntity.setStatus(PaymentStatus.FAILED);
                }
            }
            Transaction savedTransaction = transactionRepository.save(transactionEntity);
            paymentRepository.save(paymentEntity);
            return transactionMapper.mapTo(savedTransaction);
        } else  {
            throw new IllegalArgumentException("Transaction not initiated by you.");
        }
    }

    @Override
    public TransactionDTO processAnonymousTransaction(TransactionDTO transactionDTO, String ref) {
        Transaction transactionEntity = transactionMapper.mapFrom(transactionDTO);
        Payment paymentEntity = paymentRepository.findByReference(ref).orElseThrow(() -> new EntityNotFoundException("Payment request not found"));
        if (paymentEntity.getStatus() != PaymentStatus.PENDING) {
            throw new PaymentException("Payment has been processed");
        }
        boolean hasSuccessfulTransaction = paymentEntity.getTransactions().stream().anyMatch(t -> t.getStatus().equals(TransactionStatus.SUCCESS));
        if (hasSuccessfulTransaction) {
            throw new PaymentException("Payment already has successful transaction");
        }
        transactionEntity.setPayment(paymentEntity);
        transactionEntity.setTransactionReference("TXN_" + UUID.randomUUID().toString().substring(0, 8));
        boolean isSuccessful = Math.random() > 0.5;
        if (paymentEntity.getCustomerName() != null && paymentEntity.getCustomerEmail() != null && paymentEntity.getCustomerPhone() != null) {
            if (isSuccessful) {
                transactionEntity.setStatus(TransactionStatus.SUCCESS);
                paymentEntity.setStatus(PaymentStatus.PAID);
                merchantService.creditWallet(paymentEntity.getMerchant().getId(),
                        paymentEntity.getAmount());
            } else {
                transactionEntity.setStatus(TransactionStatus.FAILED);
                long failedAttempts = paymentEntity.getTransactions().stream()
                        .filter(t -> t.getStatus().equals(TransactionStatus.FAILED))
                        .count() + 1;
                if (failedAttempts >= 3) {
                    paymentEntity.setStatus(PaymentStatus.FAILED);
                }
            }
            Transaction savedTransaction = transactionRepository.save(transactionEntity);
            paymentRepository.save(paymentEntity);
            return transactionMapper.mapTo(savedTransaction);
        } else
        {
            throw new IllegalArgumentException("Customer information is required transactions.");
        }
    }

}
