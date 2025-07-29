package com.samwellstore.paymentengine.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantSignUpResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal walletBalance;
    private String businessName;
    private String callbackUrl;

}
