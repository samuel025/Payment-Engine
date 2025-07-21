package com.samwellstore.paymentengine.services.impl;

import com.samwellstore.paymentengine.Repositories.MerchantRepository;
import com.samwellstore.paymentengine.dto.MerchantDTO;
import com.samwellstore.paymentengine.entities.Merchant;
import com.samwellstore.paymentengine.services.MerchantService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class MerchantServiceImpl  implements MerchantService {
    final MerchantRepository merchantRepository;

    @Autowired
    private Mapper<Merchant, MerchantDTO> merchantMapper;

    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public MerchantDTO createMerchant(MerchantDTO merchant) {
        Merchant merchantEntity = merchantMapper.mapFrom(merchant);
        merchantEntity.setWalletBalance(BigDecimal.ZERO);
        Merchant savedMerchantEntity =  merchantRepository.save(merchantEntity);
        return  merchantMapper.mapTo(savedMerchantEntity);
    }

    @Override
    public Optional<BigDecimal> getMerchantWalletBalance(Long id) {
      return merchantRepository.findById(id).map(Merchant::getWalletBalance);
    }

    @Override
    public void creditWallet(Long id, BigDecimal amount) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
        merchant.setWalletBalance(merchant.getWalletBalance().add(amount));
        merchantRepository.save(merchant);
    }
}
