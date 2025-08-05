package com.samwellstore.paymentengine.Repositories;

import com.samwellstore.paymentengine.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.role = 'MERCHANT'")
    Optional<User> findMerchantById(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.role = 'CUSTOMER'")
    Optional<User> findCustomerById(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.role = 'MERCHANT'")
    List<User> findAllMerchants();

    @Query("SELECT u FROM User u WHERE u.role = 'CUSTOMER'")
    List<User> findAllCustomers();
}