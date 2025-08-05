package com.samwellstore.paymentengine.dto.PaymentDTOs;

import java.math.BigDecimal;

import com.samwellstore.paymentengine.dto.MerchantDTOs.MerchantWithoutBalanceDTO;
import com.samwellstore.paymentengine.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private MerchantWithoutBalanceDTO merchant;
    private BigDecimal amount;
    private String reference;
    private PaymentStatus status;
    private String description;
    private String customerEmail;
    private String customerName;
    private String customerPhone;
}
