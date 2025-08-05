package com.samwellstore.paymentengine.Repositories;

import com.samwellstore.paymentengine.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByReference(String reference);
    List<Payment> findByCustomerId(Long customerId);

    List<Payment> findByMerchantId(Long merchantId);
}
