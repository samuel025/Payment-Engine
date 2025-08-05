package com.samwellstore.paymentengine.controllers;

import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import com.samwellstore.paymentengine.security.UserPrincipal;
import com.samwellstore.paymentengine.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/transaction/{ref}")
    public ResponseEntity<TransactionDTO> customerMakeTransaction(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("ref") String ref, @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.processCustomerTransaction(transactionDTO, ref, userPrincipal), HttpStatus.CREATED);
    }

    @PostMapping(path = "/anonymous/transaction/{ref}")
    public ResponseEntity<TransactionDTO> makeAnonymousTransaction(@PathVariable("ref") String ref, @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.processAnonymousTransaction(transactionDTO, ref), HttpStatus.CREATED);
    }



}
