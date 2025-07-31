package com.samwellstore.paymentengine.security;

import com.samwellstore.paymentengine.Repositories.AdminRepository;
import com.samwellstore.paymentengine.Repositories.CustomerRepository;
import com.samwellstore.paymentengine.Repositories.MerchantRepository;
import com.samwellstore.paymentengine.entities.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        BaseUser user = (BaseUser) merchantRepository.findByEmail(email).orElse(null);
        if(user == null){
            user = (BaseUser) customerRepository.findByEmail(email).orElse(null);
        }
        if(user == null){
            user = (BaseUser) adminRepository.findByEmail(email).orElse(null);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return UserPrincipal.create(user);
    }

}
