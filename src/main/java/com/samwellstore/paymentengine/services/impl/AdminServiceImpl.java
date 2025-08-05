package com.samwellstore.paymentengine.services.impl;

import com.samwellstore.paymentengine.Repositories.PaymentRepository;
import com.samwellstore.paymentengine.Repositories.TransactionRepository;
import com.samwellstore.paymentengine.Repositories.UserRepository;
import com.samwellstore.paymentengine.dto.CustomerDTOs.CustomerDTO;
import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantDTO;
import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.dto.TransactionDTOs.TransactionDTO;
import com.samwellstore.paymentengine.entities.Payment;
import com.samwellstore.paymentengine.entities.Transaction;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.exceptions.ResourceNotFoundException;
import com.samwellstore.paymentengine.services.AdminService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {
    final UserRepository userRepository;
    final TransactionRepository transactionRepository;
    final Mapper<Payment, PaymentDTO> paymentMapper;
    final Mapper<User, CustomerDTO> customerMapper;
    final Mapper<User, MerchantDTO> merchantMapper;
    final Mapper<Transaction, TransactionDTO> transactionMapper;
    final PaymentRepository paymentRepository;

    public AdminServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository, Mapper<Payment, PaymentDTO> paymentMapper, Mapper<User, CustomerDTO> customerMapper, Mapper<User, MerchantDTO> merchantMapper, Mapper<Transaction, TransactionDTO> transactionMapper, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.paymentMapper = paymentMapper;
        this.customerMapper = customerMapper;
        this.merchantMapper = merchantMapper;
        this.transactionMapper = transactionMapper;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<User> customers = userRepository.findAllCustomers();
        return customers.stream()
                .map(customerMapper::mapTo)
                .toList();
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        User customer = userRepository.findCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.mapTo(customer);
    }

    @Override
    public MerchantDTO getMerchantById(Long id) {
        User merchant = userRepository.findMerchantById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + id));
        return merchantMapper.mapTo(merchant);
    }

    @Override
    public List<MerchantDTO> getAllMerchants() {
        List<User> merchants = userRepository.findAllMerchants();
        return merchants.stream()
                .map(merchantMapper::mapTo)
                .toList();
    }

    @Override
    public Page<TransactionDTO> getAllTransactions(Pageable pageable){
        return transactionRepository.findAll(pageable).map(transactionMapper::mapTo);
    }

    @Override
    public TransactionDTO findTransactionByReference(String ref){
        Transaction transaction = transactionRepository.findByTransactionReference(ref).orElseThrow(() -> new ResourceNotFoundException("Transaction reference not found for reference : " + ref));
        return transactionMapper.mapTo(transaction);
    }

    @Override
    public PaymentDTO getPaymentByReference(String reference) {
        Payment payment = paymentRepository.findByReference(reference)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with reference: " + reference));
        return paymentMapper.mapTo(payment);
    }

}
