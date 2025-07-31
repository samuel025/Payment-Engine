package com.samwellstore.paymentengine.controllers;

import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.dto.PaymentRequestDTOs.PaymentRequestDTO;
import com.samwellstore.paymentengine.services.MerchantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MerchantController {
    final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping(path = "/merchant")
    public ResponseEntity<MerchantDTO> createMerchant(@RequestBody MerchantDTO merchantDTO) {
        return new ResponseEntity<>(merchantService.createMerchant(merchantDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/merchant/wallet/{id}")
    public ResponseEntity<BigDecimal> getWalletBalance(@PathVariable("id")  Long id) {
        return merchantService.getMerchantWalletBalance(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/merchant/payments")
    public ResponseEntity<List<PaymentRequestDTO>> getMerchantPaymentRequests(){
        return new ResponseEntity<>(merchantService.getMerchantPaymentRequests(), HttpStatus.OK);
    }

}
