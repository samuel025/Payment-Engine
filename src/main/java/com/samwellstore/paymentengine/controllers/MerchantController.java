package com.samwellstore.paymentengine.controllers;

import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.security.UserPrincipal;
import com.samwellstore.paymentengine.services.MerchantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MerchantController {
    final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping(path = "/merchant/wallet/{id}")
    public ResponseEntity<BigDecimal> getWalletBalance(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id")  Long id) {
        return merchantService.getMerchantWalletBalance(userPrincipal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/merchant/payments")
    public ResponseEntity<List<PaymentDTO>> getMerchantPaymentRequests(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(merchantService.getMerchantPaymentRequests(userPrincipal), HttpStatus.OK);
    }
}
