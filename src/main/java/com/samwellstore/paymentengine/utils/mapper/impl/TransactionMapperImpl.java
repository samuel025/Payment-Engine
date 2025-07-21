package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.MerchantDTO;
import com.samwellstore.paymentengine.dto.TransactionDTO;
import com.samwellstore.paymentengine.entities.Merchant;
import com.samwellstore.paymentengine.entities.Transaction;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements Mapper<Transaction, TransactionDTO> {
    final  ModelMapper modelMapper;
    public TransactionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionDTO mapTo(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    @Override
    public Transaction mapFrom(TransactionDTO transactionDTO) {
        return modelMapper.map(transactionDTO, Transaction.class);
    }
}
