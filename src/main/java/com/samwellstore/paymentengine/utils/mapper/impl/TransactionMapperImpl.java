package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.PaymentRequestDTO;
import com.samwellstore.paymentengine.dto.TransactionDTO;
import com.samwellstore.paymentengine.entities.PaymentRequest;
import com.samwellstore.paymentengine.entities.Transaction;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements Mapper<Transaction, TransactionDTO> {
    final  ModelMapper modelMapper;

    @Autowired
    private Mapper<PaymentRequest, PaymentRequestDTO> paymentRequestMapper;

    public TransactionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionDTO mapTo(Transaction transaction) {
            if(transaction == null) {
                return null;
            }

            TransactionDTO dto = TransactionDTO.builder()
                    .id(transaction.getId())
                    .status(transaction.getStatus())
                    .channel(transaction.getChannel())
                    .transactionReference(transaction.getTransactionReference())
                    .processedAt(transaction.getProcessedAt())
                    .build();

        if (transaction.getPaymentRequest() != null) {
            dto.setPaymentRequest(paymentRequestMapper.mapTo(transaction.getPaymentRequest()));
        }

        return dto;

    }

    @Override
    public Transaction mapFrom(TransactionDTO transactionDTO) {
        if(transactionDTO == null) {
            return null;
        }

        Transaction entity = Transaction.builder()
                .id(transactionDTO.getId())
                .status(transactionDTO.getStatus())
                .channel(transactionDTO.getChannel())
                .transactionReference(transactionDTO.getTransactionReference())
                .processedAt(transactionDTO.getProcessedAt())
                .build();

        if (transactionDTO.getPaymentRequest() != null) {
            entity.setPaymentRequest(paymentRequestMapper.mapFrom(transactionDTO.getPaymentRequest()));
        }

        return entity;

    }
}
