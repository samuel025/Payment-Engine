package com.samwellstore.paymentengine.dto.AdminDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDTO {
    private Long id;
    private String firstName;
    private  String lastName;
}
