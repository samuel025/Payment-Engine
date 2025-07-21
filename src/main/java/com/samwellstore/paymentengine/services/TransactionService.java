package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.TransactionDTO;

public interface TransactionService {
    public TransactionDTO processTransaction(TransactionDTO transactionDTO);
}
