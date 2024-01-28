package com.dipada.paymentservice.controller;

import com.dipada.paymentservice.dto.PaymentRequest;
import com.dipada.paymentservice.model.Wallet;
import com.dipada.paymentservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final WalletRepository walletRepository;

    @Autowired
    PaymentController(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @PostMapping("/topup")
    public ResponseEntity<String> makeTopUp(@RequestBody PaymentRequest paymentRequest) {
        Optional<Wallet> wallet = walletRepository.findWalletByEmail(paymentRequest.getEmail());
        if (wallet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
        }

        synchronized (wallet.get()) {
            wallet.get().setBalance(wallet.get().getBalance() + paymentRequest.getAmount());
            walletRepository.save(wallet.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body("TopUp successful");
    }

    @PostMapping("/create-wallet")
    public ResponseEntity<String> createWallet(@RequestBody PaymentRequest paymentRequest) {
        walletRepository.save(new Wallet(paymentRequest.getEmail()));
        return ResponseEntity.status(HttpStatus.OK).body("Wallet created");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> makeWithdraw(@RequestBody PaymentRequest paymentRequest) {
        Optional<Wallet> wallet = walletRepository.findWalletByEmail(paymentRequest.getEmail());
        if (wallet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
        }

        synchronized (wallet.get()) {
            if (wallet.get().getBalance() < paymentRequest.getAmount()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough money");
            }

            wallet.get().setBalance(wallet.get().getBalance() - paymentRequest.getAmount());
            walletRepository.save(wallet.get());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Payment successful");
    }
}
