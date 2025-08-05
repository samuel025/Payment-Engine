package com.samwellstore.paymentengine.dto.TransactionDTOs;

import com.samwellstore.paymentengine.dto.PaymentDTOs.PaymentDTO;
import com.samwellstore.paymentengine.enums.PaymentChannel;
import com.samwellstore.paymentengine.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private Long id;
    private PaymentDTO paymentRequest;
    private PaymentChannel channel;
    private String transactionReference;
    private TransactionStatus status;
    private LocalDateTime processedAt;

}
