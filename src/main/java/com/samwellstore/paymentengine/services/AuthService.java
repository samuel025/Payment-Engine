package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.AdminDTOs.AddAdminDTO;
import com.samwellstore.paymentengine.dto.AdminDTOs.AdminDTO;
import com.samwellstore.paymentengine.dto.AuthenticationDTOs.AuthResponseDTO;
import com.samwellstore.paymentengine.dto.AuthenticationDTOs.LoginRequestDTO;
import com.samwellstore.paymentengine.dto.AuthenticationDTOs.SignUpRequestDTO;
import jakarta.validation.Valid;

public interface AuthService {

    Object signup(SignUpRequestDTO signUpRequestDto);
    AuthResponseDTO login(LoginRequestDTO signUpRequestDto);

    AdminDTO addAdmin(@Valid AddAdminDTO addadminDTO);
}
