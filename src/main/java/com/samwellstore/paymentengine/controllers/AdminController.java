package com.samwellstore.paymentengine.controllers;

import com.samwellstore.paymentengine.dto.CustomerDTOs.CustomerDTO;
import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import com.samwellstore.paymentengine.services.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/admin")
@RestController()
public class AdminController {
    final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping(path = "/customers")
    public ResponseEntity<List<CustomerDTO>> fetchAllCustomers() {
        List<CustomerDTO> customers = adminService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping(path = "/transactions")
    public ResponseEntity<Page<TransactionDTO>> fetchAllCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(adminService.getAllTransactions(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping(path = "/customers/{id}")
    public ResponseEntity<CustomerDTO> fetchCustomerById(@PathVariable("id") Long id) {
        CustomerDTO customer = adminService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping(path = "/merchants")
    public ResponseEntity<List<MerchantDTO>> fetchAllMerchants() {
        List<MerchantDTO> merchants = adminService.getAllMerchants();
        return new ResponseEntity<>(merchants, HttpStatus.OK);
    }

    @GetMapping(path = "/merchants/{id}")
    public ResponseEntity<MerchantDTO> fetchMerchantById(@PathVariable("id") Long id) {
        MerchantDTO merchant = adminService.getMerchantById(id);
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }

    @GetMapping(path = "/transaction/{ref}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable("ref") String ref) {
        return new ResponseEntity<>(adminService.findTransactionByReference(ref), HttpStatus.OK);
    }

    @GetMapping(path = "/payment/{reference}")
    public ResponseEntity<PaymentDTO> getPaymentRequestByReference(@PathVariable("reference") String reference) {
        return new ResponseEntity<>(adminService.getPaymentByReference(reference), HttpStatus.OK);
    }


}
