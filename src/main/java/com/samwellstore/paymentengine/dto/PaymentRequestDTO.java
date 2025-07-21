package com.samwellstore.paymentengine.dto;

import com.samwellstore.paymentengine.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {
    private Long id;
    private MerchantDTO merchant;
    private BigDecimal amount;
    private String reference;
    private PaymentStatus paymentStatus;
    private String description;
    private String createdAt;
    private LocalDateTime updatedAt;
//    private List<TransactionDTO> transactions;
}
