package com.userservice.userservice.repository;

import com.userservice.userservice.model.Seller;
import com.userservice.userservice.model.User;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, User> {
    Optional<Seller> findByEmail(String email);
}
