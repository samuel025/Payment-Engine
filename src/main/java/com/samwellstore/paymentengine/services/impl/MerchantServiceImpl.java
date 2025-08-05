package com.samwellstore.paymentengine.services.impl;

import com.samwellstore.paymentengine.Repositories.PaymentRepository;
import com.samwellstore.paymentengine.Repositories.UserRepository;
import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.entities.Payment;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.enums.Roles;
import com.samwellstore.paymentengine.security.UserPrincipal;
import com.samwellstore.paymentengine.services.MerchantService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MerchantServiceImpl  implements MerchantService {
    final UserRepository userRepository;
    final PaymentRepository paymentRepository;
    final Mapper<Payment, PaymentDTO> paymentMapper;
    final Mapper<User, MerchantDTO> merchantMapper;

    public MerchantServiceImpl(PaymentRepository paymentRepository, Mapper<Payment, PaymentDTO> paymentMapper, Mapper<User, MerchantDTO> merchantMapper, UserRepository userRepository) {
        this.merchantMapper = merchantMapper;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Optional<BigDecimal> getMerchantWalletBalance(UserPrincipal userPrincipal) {
      return userRepository.findMerchantById(userPrincipal.getId()).map(User::getWalletBalance);
    }

    @Override
    public void creditWallet(Long id, BigDecimal amount) {
        User merchant = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
        merchant.setWalletBalance(merchant.getWalletBalance().add(amount));
        userRepository.save(merchant);
    }

    @Override
    public List<PaymentDTO> getMerchantPaymentRequests(UserPrincipal userPrincipal) {
            if(userPrincipal.getRole().equals(Roles.MERCHANT)){
                List<Payment> payments = paymentRepository.findByMerchantId(userPrincipal.getId());
                return payments.stream()
                        .map(paymentMapper::mapTo)
                        .toList();
            } else {
                throw new IllegalArgumentException("Only merchants can access their payment requests");
            }
    }
}
