package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.security.UserPrincipal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface MerchantService {
     Optional<BigDecimal> getMerchantWalletBalance(UserPrincipal userPrincipal);
     void creditWallet(Long id, BigDecimal amount);
    List<PaymentDTO> getMerchantPaymentRequests(UserPrincipal userPrincipal);
}
