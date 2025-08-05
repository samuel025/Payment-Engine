package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.CustomerDTOs.CustomerDTO;
import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    public List<CustomerDTO> getAllCustomers();
    public CustomerDTO getCustomerById(Long id);
    public MerchantDTO getMerchantById(Long id);
    public List<MerchantDTO> getAllMerchants();
    public Page<TransactionDTO> getAllTransactions(Pageable pageable);
    public TransactionDTO findTransactionByReference(String ref);
    public PaymentDTO getPaymentByReference(String reference);
}
