package com.samwellstore.paymentengine.controllers;


import com.samwellstore.paymentengine.dto.PaymentRequestDTO;
import com.samwellstore.paymentengine.services.PaymentRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentRequestController {
    final PaymentRequestService paymentRequestService;

    public PaymentRequestController(PaymentRequestService paymentRequestService) {
        this.paymentRequestService = paymentRequestService;
    }

    @PostMapping(path = "/payment")
    public ResponseEntity<PaymentRequestDTO> createPaymentRequest(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentRequestDTO savedPaymentRequest = paymentRequestService.createPayment(paymentRequestDTO);
        return new ResponseEntity<>(savedPaymentRequest, HttpStatus.CREATED);
    }

    @GetMapping(path = "/payment/{reference}")
    public ResponseEntity<PaymentRequestDTO> getPaymentRequestByReference(@PathVariable("reference") String reference) {
        return new ResponseEntity<>(paymentRequestService.getPaymentByReference(reference), HttpStatus.OK);
    }
}
