package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import com.samwellstore.paymentengine.security.UserPrincipal;

import java.util.List;


public interface PaymentService {
    public PaymentDTO createPayment(PaymentDTO paymentDTO, UserPrincipal userPrincipal, Long merchantId);
    public PaymentDTO createAnonymousPayment(PaymentDTO paymentDTO, Long merchantId);
    List<PaymentDTO> getPayments(UserPrincipal userPrincipal);

    List<TransactionDTO> customerGetTransactionsByPaymentRequest(String paymentReference, UserPrincipal userPrincipal);
}
