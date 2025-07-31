package com.samwellstore.paymentengine.dto.MerchantDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String businessName;
    private String callbackUrl;
    private String walletBalance;
}
