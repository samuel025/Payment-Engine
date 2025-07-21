package com.samwellstore.paymentengine.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "callback_url", nullable = false)
    private String callbackUrl;

    @Column(name = "wallet_balance", precision = 10, scale = 2)
    private BigDecimal walletBalance=BigDecimal.ZERO;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentRequest> paymentRequests;



}
