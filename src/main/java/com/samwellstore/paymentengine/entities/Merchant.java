package com.samwellstore.paymentengine.entities;

import com.samwellstore.paymentengine.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "merchants")
public class Merchant extends BaseUser {

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "callback_url")
    private String callbackUrl;

    @Column(name = "wallet_balance", precision = 10, scale = 2)
    private BigDecimal walletBalance = BigDecimal.ZERO;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentRequest> paymentRequests;

    @Override
    public Roles getUserType() {
        return Roles.MERCHANT;
    }
}
