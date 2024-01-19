package com.userservice.userservice.repository;

import com.userservice.userservice.model.Client;
import com.userservice.userservice.model.Seller;
import com.userservice.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findSellerByUser(User user);
}
