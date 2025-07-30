package com.samwellstore.paymentengine.services.impl;

import com.samwellstore.paymentengine.Repositories.PaymentRequestRepository;
import com.samwellstore.paymentengine.Repositories.TransactionRepository;
import com.samwellstore.paymentengine.dto.TransactionDTO;
import com.samwellstore.paymentengine.entities.PaymentRequest;
import com.samwellstore.paymentengine.entities.Transaction;
import com.samwellstore.paymentengine.enums.PaymentStatus;
import com.samwellstore.paymentengine.enums.Roles;
import com.samwellstore.paymentengine.enums.TransactionStatus;
import com.samwellstore.paymentengine.security.UserPrincipal;
import com.samwellstore.paymentengine.services.MerchantService;
import com.samwellstore.paymentengine.services.TransactionService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private Mapper<Transaction, TransactionDTO> transactionMapper;

    final MerchantService merchantService;
    final TransactionRepository transactionRepository;
    final PaymentRequestRepository paymentRequestRepository;

    public TransactionServiceImpl(MerchantService merchantService, TransactionRepository transactionRepository, PaymentRequestRepository paymentRequestRepository) {
        this.merchantService = merchantService;
        this.transactionRepository = transactionRepository;
        this.paymentRequestRepository = paymentRequestRepository;
    }

    @Override
    @Transactional
    public TransactionDTO processTransaction(TransactionDTO transactionDTO, String ref) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Transaction transactionEntity = transactionMapper.mapFrom(transactionDTO);
        PaymentRequest paymentEntity = paymentRequestRepository.findByReference(ref).orElseThrow(() -> new EntityNotFoundException("Payment request not found"));
        if(paymentEntity.getStatus() != PaymentStatus.PENDING){
            throw new RuntimeException("Payment request has been processed");
        }
        boolean hasSuccessfulTransaction = paymentEntity.getTransactions().stream().anyMatch(t -> t.getStatus().equals(TransactionStatus.SUCCESS));
        if(hasSuccessfulTransaction){
            throw new RuntimeException("Payment already has successful transaction");
        }
        transactionEntity.setPaymentRequest(paymentEntity);
        transactionEntity.setTransactionReference("TXN_" + UUID.randomUUID().toString().substring(0, 8));
        boolean isSuccessful = Math.random() > 0.5;

        //If user is authenticated and is a customer that made the payment request
        if(authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal && ((UserPrincipal) authentication.getPrincipal()).getUserType().equals(Roles.CUSTOMER) && paymentEntity.getCustomer().getId().equals(userPrincipal.getId())) {
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
            paymentRequestRepository.save(paymentEntity);
            return transactionMapper.mapTo(savedTransaction);

            //If user is not authenticated (Anonymous user)
        } else if (authentication != null  && authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ANONYMOUS"))
                && paymentEntity.getCustomerName() != null
                && paymentEntity.getCustomerEmail() != null
                && paymentEntity.getCustomerPhone() != null) {
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
            paymentRequestRepository.save(paymentEntity);
            return transactionMapper.mapTo(savedTransaction);
        } else  {
            throw new IllegalArgumentException("Invalid transaction request. Ensure you are authenticated or provide valid customer details.");
        }
    }

    @Override
    public TransactionDTO findTransactionByReference(String ref){
        Transaction transaction = transactionRepository.findByTransactionReference(ref).orElseThrow(() -> new EntityNotFoundException("Transaction reference not found"));
        return transactionMapper.mapTo(transaction);
    }

    @Override
    public Page<TransactionDTO> getAllTransactions(Pageable pageable){
        return transactionRepository.findAll(pageable).map(transactionMapper::mapTo);
    }

}
