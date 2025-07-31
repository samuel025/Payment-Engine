package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.dto.PaymentRequestDTOs.PaymentRequestDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface MerchantService {
     MerchantDTO createMerchant(MerchantDTO merchant);
     Optional<BigDecimal> getMerchantWalletBalance(Long id);
     void creditWallet(Long id, BigDecimal amount);

    List<PaymentRequestDTO> getMerchantPaymentRequests();

}
