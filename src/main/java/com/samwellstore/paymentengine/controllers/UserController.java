package com.samwellstore.paymentengine.controllers;


import com.samwellstore.paymentengine.dto.AuthResponseDTO;
import com.samwellstore.paymentengine.dto.LoginRequestDTO;
import com.samwellstore.paymentengine.dto.SignUpRequestDTO;
import com.samwellstore.paymentengine.dto.CustomerSignUpResponseDTO;
import com.samwellstore.paymentengine.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        Object responseObject = authService.signup(signUpRequestDTO);
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO authResponseDTO = authService.login(loginRequestDTO);
        return ResponseEntity.ok(authResponseDTO);
    }
}
