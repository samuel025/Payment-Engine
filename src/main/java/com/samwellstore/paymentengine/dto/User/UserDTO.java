package com.samwellstore.paymentengine.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private  String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private String address;
    private String role;
    private String businessName;
    private String callBackUrl;
    private String walletBalance;
}

