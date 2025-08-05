package com.samwellstore.paymentengine.controllers;


import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import com.samwellstore.paymentengine.security.UserPrincipal;
import com.samwellstore.paymentengine.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {
    final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(path = "/payment/{merchantId}")
    public ResponseEntity<PaymentDTO> customerCreatePaymentRequest(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody PaymentDTO paymentDTO, @PathVariable("merchantId") Long merchantId) {
        PaymentDTO savedPaymentRequest = paymentService.createPayment(paymentDTO, userPrincipal, merchantId);
        return new ResponseEntity<>(savedPaymentRequest, HttpStatus.CREATED);
    }

    @PostMapping(path = "/anonymous/payment/{merchantId}")
    public ResponseEntity<PaymentDTO> createAnonymousPaymentRequest(@RequestBody PaymentDTO paymentDTO, @PathVariable("merchantId") Long merchantId) {
        PaymentDTO savedPaymentRequest = paymentService.createAnonymousPayment(paymentDTO, merchantId);
        return new ResponseEntity<>(savedPaymentRequest, HttpStatus.CREATED);
    }


    @GetMapping(path = "/payment")
    public ResponseEntity<List<PaymentDTO>> getPaymentRequests(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<PaymentDTO> paymentDTOS = paymentService.getPayments(userPrincipal);
        return new ResponseEntity<>(paymentDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "/payment/transactions/{paymentReference}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByPaymentRequest(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("paymentReference") String paymentReference) {
        List<TransactionDTO> transactions = paymentService.customerGetTransactionsByPaymentRequest(paymentReference, userPrincipal);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
