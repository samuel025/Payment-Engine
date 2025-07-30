package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.PaymentRequestDTO;

import java.util.List;


public interface PaymentRequestService {
    public PaymentRequestDTO createPayment(PaymentRequestDTO paymentRequestDTO);
    public PaymentRequestDTO getPaymentByReference(String reference);

    List<PaymentRequestDTO> getPayments();

}
