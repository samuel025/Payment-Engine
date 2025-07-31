package com.samwellstore.paymentengine.services.impl;

import com.samwellstore.paymentengine.Repositories.AdminRepository;
import com.samwellstore.paymentengine.dto.AdminDTOs.AddAdminDTO;
import com.samwellstore.paymentengine.dto.AdminDTOs.AdminDTO;
import com.samwellstore.paymentengine.dto.AuthenticationDTOs.AuthResponseDTO;
import com.samwellstore.paymentengine.dto.AuthenticationDTOs.LoginRequestDTO;
import com.samwellstore.paymentengine.dto.AuthenticationDTOs.SignUpRequestDTO;
import com.samwellstore.paymentengine.dto.CustomerDTOs.CustomerSignUpResponseDTO;
import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantSignUpResponseDTO;
import com.samwellstore.paymentengine.entities.Admin;
import com.samwellstore.paymentengine.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AdminRepository adminRepository;

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

    @Autowired
    private Mapper<Admin, AddAdminDTO> addAdminDTOMapper;

    @Autowired
    private Mapper<Admin, AdminDTO> adminDTOMapper;

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

    @Override
    public AdminDTO addAdmin(AddAdminDTO addAdminDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal){
            if( !userPrincipal.getUserType().equals(Roles.ADMIN)) {
                throw new RuntimeException("Only admins can add other admins");
            }
            String hashedPassword = bCryptPasswordEncoder.encode(addAdminDTO.getPassword());
            addAdminDTO.setPassword(hashedPassword);
            Admin adminEntity = addAdminDTOMapper.mapFrom(addAdminDTO);
            return adminDTOMapper.mapTo(adminRepository.save(adminEntity));
        } else {
            throw new IllegalArgumentException("Admin Authentication is required to add an admin");
        }
    }
}
