package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.PaymentRequestDTO;


public interface PaymentRequestService {
    public PaymentRequestDTO createPayment(PaymentRequestDTO paymentRequestDTO);
    public PaymentRequestDTO getPaymentByReference(String reference);
}
