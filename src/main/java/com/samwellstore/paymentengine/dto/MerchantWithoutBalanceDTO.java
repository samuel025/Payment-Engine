package com.samwellstore.paymentengine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantWithoutBalanceDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String businessName;
}
