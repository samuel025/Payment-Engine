package com.samwellstore.paymentengine.services.impl;

import com.samwellstore.paymentengine.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.samwellstore.paymentengine.Repositories.CustomerRepository;
import com.samwellstore.paymentengine.Repositories.MerchantRepository;
import com.samwellstore.paymentengine.entities.Customer;
import com.samwellstore.paymentengine.entities.Merchant;
import com.samwellstore.paymentengine.enums.Roles;
import com.samwellstore.paymentengine.services.AuthService;
import com.samwellstore.paymentengine.security.JWTService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private Mapper<Merchant, MerchantSignUpResponseDTO> merchantToSignUpResponseDTOMapper;

    @Autowired
    private Mapper<Merchant, SignUpRequestDTO> signupRequestToMerchantMapper;

    @Autowired
    private Mapper<Customer, SignUpRequestDTO> signupRequestToCustomerMapper;

    @Autowired
    private Mapper<Customer, CustomerSignUpResponseDTO> customerToSignUpResponseDTOMapper;

    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public Object signup(SignUpRequestDTO signUpRequestDto) {
        if(merchantRepository.existsByEmail(signUpRequestDto.getEmail()) || customerRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Roles userType = signUpRequestDto.getRole();
        if (userType == Roles.MERCHANT) {
            Merchant merchant = signupRequestToMerchantMapper.mapFrom(signUpRequestDto);
            merchant.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
            Merchant savedMerchant = merchantRepository.save(merchant);
            return merchantToSignUpResponseDTOMapper.mapTo(savedMerchant);
        } else if (userType == Roles.CUSTOMER) {
            Customer customer = signupRequestToCustomerMapper.mapFrom(signUpRequestDto);
            customer.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
            Customer savedCustomer = customerRepository.save(customer);
            return customerToSignUpResponseDTOMapper.mapTo(savedCustomer);
        } else {
            throw new RuntimeException("Invalid user type");
        }

    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authentication);
            return new AuthResponseDTO(
                    token,
                    authentication.getAuthorities().stream().findFirst().orElseThrow().getAuthority()
            );
        }
        return null;
    }
}
