package com.samwellstore.paymentengine.controllers;

import com.samwellstore.paymentengine.dto.MerchantDTO;
import com.samwellstore.paymentengine.services.MerchantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

}
