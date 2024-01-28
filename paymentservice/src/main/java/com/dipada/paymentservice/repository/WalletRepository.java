package com.dipada.paymentservice.repository;

import com.dipada.paymentservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
    Optional<Wallet> findWalletByEmail(String email);
}
