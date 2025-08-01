package com.samwellstore.paymentengine.Repositories;

import com.samwellstore.paymentengine.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByEmail(String email);
    boolean existsByEmail(String email);

}
