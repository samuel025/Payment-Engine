package com.samwellstore.paymentengine.dto.CustomerDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSignUpResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;

    //If merchant
//    private String businessName;
//    private String callbackUrl;
//    private String walletBalance;
}
