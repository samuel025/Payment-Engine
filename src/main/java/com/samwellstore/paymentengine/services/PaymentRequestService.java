package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.PaymentRequestDTOs.PaymentRequestDTO;
import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;

import java.util.List;


public interface PaymentRequestService {
    public PaymentRequestDTO createPayment(PaymentRequestDTO paymentRequestDTO);
    public PaymentRequestDTO getPaymentByReference(String reference);

    List<PaymentRequestDTO> getPayments();

    List<TransactionDTO> getTransactionsByPaymentRequest(Long paymentRequestId);
}
