package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.MerchantDTO;
import com.samwellstore.paymentengine.entities.Merchant;

import java.math.BigDecimal;
import java.util.Optional;

public interface MerchantService {
    public MerchantDTO createMerchant(MerchantDTO merchant);
    public Optional<BigDecimal> getMerchantWalletBalance(Long id);
    public void creditWallet(Long id, BigDecimal amount);
}
