package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import com.samwellstore.paymentengine.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    public TransactionDTO processCustomerTransaction(TransactionDTO transactionDTO, String ref, UserPrincipal userPrincipal);
    public TransactionDTO processAnonymousTransaction(TransactionDTO transactionDTO, String ref);


}
