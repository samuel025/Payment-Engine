package com.samwellstore.paymentengine.controllers;

import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import com.samwellstore.paymentengine.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/transaction/{ref}")
    public ResponseEntity<TransactionDTO> processTransaction(@PathVariable("ref") String ref, @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.processTransaction(transactionDTO, ref), HttpStatus.CREATED);
    }

    @GetMapping(path = "/transaction/{ref}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable("ref") String ref) {
        return new ResponseEntity<>(transactionService.findTransactionByReference(ref), HttpStatus.OK);
    }

    @GetMapping(path = "/transactions")
    public ResponseEntity<Page<TransactionDTO>> getAllTransactions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(transactionService.getAllTransactions(PageRequest.of(page, size)), HttpStatus.OK);
    }
}
