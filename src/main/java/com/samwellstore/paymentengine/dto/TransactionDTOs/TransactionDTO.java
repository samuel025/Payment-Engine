package com.samwellstore.paymentengine.dto.TransactionDTOs;

import com.samwellstore.paymentengine.dto.PaymentRequestDTOs.PaymentRequestDTO;
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
    private PaymentRequestDTO paymentRequest;
    private PaymentChannel channel;
    private String transactionReference;
    private TransactionStatus status;
    private LocalDateTime processedAt;

}
