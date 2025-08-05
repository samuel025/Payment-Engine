package com.samwellstore.paymentengine.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.samwellstore.paymentengine.Repositories.UserRepository;
import com.samwellstore.paymentengine.dto.LoginDTO.AuthResponseDTO;
import com.samwellstore.paymentengine.dto.LoginDTO.LoginRequestDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.AddAdminDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.CustomerSignUpResponseDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.CustomerSignupDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.MerchantSignUpDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.MerchantSignUpResponseDTO;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.enums.Roles;
import com.samwellstore.paymentengine.security.JWTService;
import com.samwellstore.paymentengine.security.UserPrincipal;
import com.samwellstore.paymentengine.services.AuthService;
import com.samwellstore.paymentengine.utils.mapper.Mapper;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    final UserRepository userRepository;
    final AuthenticationManager authenticationManager;
    final JWTService jwtService;
    final Mapper<User, MerchantSignUpDTO> merchantSignupMaper;
    final Mapper<User, CustomerSignupDTO> customerSignUpMapper;
    final Mapper<User, AddAdminDTO> addAdminMapper;
    final Mapper<MerchantSignUpResponseDTO, MerchantSignUpDTO> merchantSignUpResponseMapper;
    final Mapper<CustomerSignUpResponseDTO, CustomerSignupDTO> customerSignUpResponseMapper;

    public AuthServiceImpl(UserRepository userRepository,
                           AuthenticationManager authenticationManager,
                           JWTService jwtService, Mapper<User, MerchantSignUpDTO> merchantSignupMaper,
                           Mapper<User, CustomerSignupDTO> customerSignUpMapper,
                           Mapper<User, AddAdminDTO> addAdminMapper, Mapper<MerchantSignUpResponseDTO, MerchantSignUpDTO> merchantSignUpResponseMapper, Mapper<CustomerSignUpResponseDTO, CustomerSignupDTO> customerSignUpResponseMapper) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.merchantSignupMaper = merchantSignupMaper;
        this.customerSignUpMapper = customerSignUpMapper;
        this.addAdminMapper = addAdminMapper;
        this.merchantSignUpResponseMapper = merchantSignUpResponseMapper;
        this.customerSignUpResponseMapper = customerSignUpResponseMapper;
    }

    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public MerchantSignUpResponseDTO merchantSignup(MerchantSignUpDTO merchantSignUpDto) {
        log.info("Signing up merchant with: {}", merchantSignUpDto);
        if(userRepository.existsByEmail(merchantSignUpDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Roles userType = merchantSignUpDto.getRole();
        if(userType == Roles.MERCHANT) {
            User merchant = merchantSignupMaper.mapFrom(merchantSignUpDto);
            merchant.setPassword(bCryptPasswordEncoder.encode(merchant.getPassword()));
            merchant.setIsActive(true);
            merchant.setWalletBalance(BigDecimal.ZERO);
            User savedMerchant = userRepository.save(merchant);
            MerchantSignUpDTO savedMerchantDTO =  merchantSignupMaper.mapTo(savedMerchant);
            return merchantSignUpResponseMapper.mapFrom(savedMerchantDTO);
        } else {
            throw new IllegalArgumentException("Invalid user type for merchant signup");
        }
    }

    @Override
    public CustomerSignUpResponseDTO customerSignup(CustomerSignupDTO customerSignupDTO) {
        log.info("Signing up customer with: {}", customerSignupDTO);
        if(userRepository.existsByEmail(customerSignupDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Roles userType = customerSignupDTO.getRole();
        if(userType == Roles.CUSTOMER) {
            User customer = customerSignUpMapper.mapFrom(customerSignupDTO);
            customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
            customer.setIsActive(true);
            User savedCustomer = userRepository.save(customer);
            CustomerSignupDTO savedCustomerDTO = customerSignUpMapper.mapTo(savedCustomer);
            return customerSignUpResponseMapper.mapFrom(savedCustomerDTO);
        } else {
            throw new IllegalArgumentException("Invalid user type for customer signup");
        }
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        log.info("Attempting login for user: {}", loginRequestDTO.getEmail());
        
        try {
            // Check if user exists
            User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            
            // Attempt authentication
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getEmail(), 
                    loginRequestDTO.getPassword()
                )
            );
            
            if (authentication.isAuthenticated()) {
                log.info("User authenticated successfully: {}", loginRequestDTO.getEmail());
                String token = jwtService.generateToken(authentication);
                return new AuthResponseDTO(
                    token,
                    "ROLE_" + user.getRole().name()
                );
            }
            
            throw new BadCredentialsException("Invalid credentials");
        } catch (Exception e) {
            log.error("Login failed for user {}: {}", loginRequestDTO.getEmail(), e.getMessage());
            throw e;
        }
    }

    @Override
    public AddAdminDTO addAdmin(AddAdminDTO addAdminDTO) {
        log.info("Adding admin: {}", addAdminDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal){
            if( !userPrincipal.getRole().equals(Roles.ADMIN)) {
                throw new RuntimeException("Only admins can add other admins");
            }
            String hashedPassword = bCryptPasswordEncoder.encode(addAdminDTO.getPassword());
            addAdminDTO.setPassword(hashedPassword);
            User adminEntity = addAdminMapper.mapFrom(addAdminDTO);
            return addAdminMapper.mapTo(userRepository.save(adminEntity));
        } else {
            throw new IllegalArgumentException("Admin Authentication is required to add an admin");
        }
    }
}
