package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.AuthenticationDTOs.AuthResponseDTO;
import com.samwellstore.paymentengine.dto.AuthenticationDTOs.LoginRequestDTO;
import com.samwellstore.paymentengine.dto.AuthenticationDTOs.SignUpRequestDTO;

public interface AuthService {

    Object signup(SignUpRequestDTO signUpRequestDto);
    AuthResponseDTO login(LoginRequestDTO signUpRequestDto);
}
