package com.samwellstore.paymentengine.services.impl;

import com.samwellstore.paymentengine.Repositories.MerchantRepository;
import com.samwellstore.paymentengine.Repositories.PaymentRequestRepository;
import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.dto.PaymentRequestDTOs.PaymentRequestDTO;
import com.samwellstore.paymentengine.entities.Merchant;
import com.samwellstore.paymentengine.entities.PaymentRequest;
import com.samwellstore.paymentengine.enums.Roles;
import com.samwellstore.paymentengine.security.UserPrincipal;
import com.samwellstore.paymentengine.services.MerchantService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MerchantServiceImpl  implements MerchantService {
    final MerchantRepository merchantRepository;
    final PaymentRequestRepository paymentRequestRepository;
    final Mapper<PaymentRequest, PaymentRequestDTO> paymentMapper;
    final Mapper<Merchant, MerchantDTO> merchantMapper;

    public MerchantServiceImpl(MerchantRepository merchantRepository, PaymentRequestRepository paymentRequestRepository, Mapper<PaymentRequest, PaymentRequestDTO> paymentMapper, Mapper<Merchant, MerchantDTO> merchantMapper) {
        this.merchantMapper = merchantMapper;
        this.merchantRepository = merchantRepository;
        this.paymentRequestRepository = paymentRequestRepository;
        this.paymentMapper = paymentMapper;
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

    @Override
    public List<PaymentRequestDTO> getMerchantPaymentRequests() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal){
            if(userPrincipal.getUserType().equals(Roles.MERCHANT)){
                List<PaymentRequest> paymentRequests = paymentRequestRepository.findByMerchantId(userPrincipal.getId());
                return paymentRequests.stream()
                        .map(paymentMapper::mapTo)
                        .toList();
            } else {
                throw new IllegalArgumentException("Only merchants can access their payment requests");
            }
        } else {
            throw new RuntimeException("Authentication is required to access payment requests");
        }
    }
}
