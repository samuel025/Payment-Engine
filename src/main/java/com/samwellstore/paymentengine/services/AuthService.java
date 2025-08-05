package com.samwellstore.paymentengine.services;

import com.samwellstore.paymentengine.dto.SignUpDTOs.*;
import com.samwellstore.paymentengine.dto.LoginDTO.AuthResponseDTO;
import com.samwellstore.paymentengine.dto.LoginDTO.LoginRequestDTO;
import com.samwellstore.paymentengine.security.UserPrincipal;
import jakarta.validation.Valid;

public interface AuthService {

    MerchantSignUpResponseDTO merchantSignup(MerchantSignUpDTO merchantSignUpDto);
    CustomerSignUpResponseDTO customerSignup(CustomerSignupDTO customerSignupDTO);
    AuthResponseDTO login(LoginRequestDTO signUpRequestDto);

    AddAdminDTO addAdmin(@Valid AddAdminDTO addadminDTO, UserPrincipal userPrincipal);
}
