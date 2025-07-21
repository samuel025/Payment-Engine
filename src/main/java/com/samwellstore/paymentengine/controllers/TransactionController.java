package com.samwellstore.paymentengine.controllers;

import com.samwellstore.paymentengine.dto.TransactionDTO;
import com.samwellstore.paymentengine.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/transaction")
    public ResponseEntity<TransactionDTO> processTransaction(@RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.processTransaction(transactionDTO), HttpStatus.CREATED);
    }
}
