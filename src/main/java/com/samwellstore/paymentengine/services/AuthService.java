package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.AuthResponseDTO;
import com.samwellstore.paymentengine.dto.LoginRequestDTO;
import com.samwellstore.paymentengine.dto.SignUpRequestDTO;

public interface AuthService {

    Object signup(SignUpRequestDTO signUpRequestDto);
    AuthResponseDTO login(LoginRequestDTO signUpRequestDto);
}
