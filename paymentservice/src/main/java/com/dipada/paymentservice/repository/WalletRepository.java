package com.dipada.paymentservice.repository;

import com.dipada.paymentservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>{
    Optional<Wallet> findWalletByEmail(String email);
}
