package com.samwellstore.paymentengine.dto.PaymentRequestDTOs;

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
public class PaymentRequestDTO {
    private Long id;
    private MerchantWithoutBalanceDTO merchant;
    private BigDecimal amount;
    private String reference;
    private PaymentStatus status;
    private String description;
//    private List<TransactionDTO> transactions;

    private String customerEmail;
    private String customerName;
    private String customerPhone;
}
