package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    public TransactionDTO processTransaction(TransactionDTO transactionDTO, String ref);

    public TransactionDTO findTransactionByReference(String ref);
    public Page<TransactionDTO> getAllTransactions(Pageable pageable);
}
