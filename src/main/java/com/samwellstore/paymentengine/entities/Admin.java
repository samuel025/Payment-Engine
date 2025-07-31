package com.samwellstore.paymentengine.entities;


import com.samwellstore.paymentengine.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "Admin")
public class Admin extends BaseUser {

    @Override
    public Roles getUserType() {
        return Roles.ADMIN;
    }
}
