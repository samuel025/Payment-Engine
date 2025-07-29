package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.MerchantDTO;
import com.samwellstore.paymentengine.entities.Merchant;

import java.math.BigDecimal;
import java.util.Optional;


public interface MerchantService {
     MerchantDTO createMerchant(MerchantDTO merchant);
     Optional<BigDecimal> getMerchantWalletBalance(Long id);
     void creditWallet(Long id, BigDecimal amount);
}
