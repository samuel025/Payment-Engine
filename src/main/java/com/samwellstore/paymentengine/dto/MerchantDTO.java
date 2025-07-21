package com.samwellstore.paymentengine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantDTO {
    private Long id;
    private String name;
    private String businessName;
    private String callbackUrl;
    private String walletBalance;
}
