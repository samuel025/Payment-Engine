package com.samwellstore.paymentengine.controllers;


import com.samwellstore.paymentengine.dto.SignUpDTOs.*;
import com.samwellstore.paymentengine.dto.LoginDTO.AuthResponseDTO;
import com.samwellstore.paymentengine.dto.LoginDTO.LoginRequestDTO;
import com.samwellstore.paymentengine.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    @PostMapping(path = "/register/merchant")
    public ResponseEntity<MerchantSignUpResponseDTO> registerUser(@Valid @RequestBody MerchantSignUpDTO merchantSignUpDTO) {
        MerchantSignUpResponseDTO merchantSignUpResponseDTO = authService.merchantSignup(merchantSignUpDTO);
        return ResponseEntity.ok(merchantSignUpResponseDTO);
    }

    @PostMapping(path = "/register/customer")
    public ResponseEntity<CustomerSignUpResponseDTO> registerCustomer(@Valid @RequestBody CustomerSignupDTO customerSignupDTO) {
        CustomerSignUpResponseDTO customerSignUpResponseDTO = authService.customerSignup(customerSignupDTO);
        return ResponseEntity.ok(customerSignUpResponseDTO);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO authResponseDTO = authService.login(loginRequestDTO);
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AddAdminDTO> addAdmin(@Valid @RequestBody AddAdminDTO addAdminDTO) {
        AddAdminDTO createdAdmin = authService.addAdmin(addAdminDTO);
        return ResponseEntity.ok(createdAdmin);
    }

    @GetMapping(path = "/test/{password}")
    public ResponseEntity<String> testEndpoint(@PathVariable("password") String password) {
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        return new ResponseEntity<>(hashedPassword, HttpStatus.OK);
    }
}
