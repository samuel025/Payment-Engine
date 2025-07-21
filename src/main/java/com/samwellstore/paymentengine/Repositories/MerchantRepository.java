package com.samwellstore.paymentengine.Repositories;

import com.samwellstore.paymentengine.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
