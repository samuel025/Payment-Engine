package com.samwellstore.paymentengine.Repositories;

import com.samwellstore.paymentengine.entities.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    Optional<PaymentRequest> findByReference(String reference);
}
