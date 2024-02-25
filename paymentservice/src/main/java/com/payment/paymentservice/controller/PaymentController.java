package com.payment.paymentservice.controller;

import com.payment.paymentservice.dto.PaymentOutcome;
import com.payment.paymentservice.dto.PaymentRequest;
import com.payment.paymentservice.model.Wallet;
import com.payment.paymentservice.rabbitMQ.RabbitMQSender;
import com.payment.paymentservice.repository.WalletRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	private final WalletRepository walletRepository;

	private final RabbitMQSender rabbitMQSender;

	@Autowired
	PaymentController(WalletRepository walletRepository, RabbitMQSender rabbitMQSender) {
		this.walletRepository = walletRepository;
		this.rabbitMQSender = rabbitMQSender;
	}

	@Operation(summary = "Make a topup to a wallet")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "TopUp successful"), @ApiResponse(responseCode = "404", description = "Wallet not found"),})
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

	@Operation(summary = "Create a wallet")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Wallet created"),})
	@PostMapping("/create-wallet")
	public ResponseEntity<String> createWallet(@RequestBody PaymentRequest paymentRequest) {
		walletRepository.save(new Wallet(paymentRequest.getEmail()));
		return ResponseEntity.status(HttpStatus.OK).body("Wallet created");
	}

	@Operation(summary = "Make a withdraw from a wallet")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Payment successful"), @ApiResponse(responseCode = "404", description = "Wallet not found"), @ApiResponse(responseCode = "400", description = "Not enough money"),})
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

	@Operation(summary = "Get wallet balance")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Balance retrieved"), @ApiResponse(responseCode = "404", description = "Wallet not found"),})
	@GetMapping("/balance/{email}")
	public ResponseEntity<String> getBalance(@PathVariable String email) {
		Optional<Wallet> wallet = walletRepository.findWalletByEmail(email);
		return wallet.map(value -> ResponseEntity.status(HttpStatus.OK).body(String.valueOf(value.getBalance()))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found"));
	}

	@Operation(summary = "Make a payment")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Payment successful"), @ApiResponse(responseCode = "404", description = "Wallet not found"), @ApiResponse(responseCode = "400", description = "Not enough money"),})
	@PostMapping("/pay")
	public ResponseEntity<String> makePayment(@RequestBody PaymentRequest paymentRequest) {
		Optional<Wallet> wallet = walletRepository.findWalletByEmail(paymentRequest.getEmail());
		if (wallet.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
		}

		synchronized (wallet.get()) {
			if (wallet.get().getBalance() <= paymentRequest.getAmount()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough money");
			}

			RestTemplate restTemplate = new RestTemplate();
			String url = "http://orderservice:8086/api/v1/order/get-order-shop/" + paymentRequest.getOrderId();
			Long shopId = restTemplate.getForEntity(url, Long.class).getBody();
			String sellerEmail = restTemplate.getForEntity("http://shopservice:8082/shop/seller-email/" + shopId, String.class).getBody();

			PaymentRequest paymentRequestSeller = new PaymentRequest();
			paymentRequestSeller.setEmail(sellerEmail);
			paymentRequestSeller.setAmount(paymentRequest.getAmount());
			makeTopUp(paymentRequestSeller);

			wallet.get().setBalance(wallet.get().getBalance() - paymentRequest.getAmount());
			walletRepository.save(wallet.get());
			PaymentOutcome paymentOutcome = new PaymentOutcome();
			paymentOutcome.setOrderId(paymentRequest.getOrderId());
			paymentOutcome.setPaid(true);
			paymentOutcome.setUserEmail(paymentRequest.getEmail());

			rabbitMQSender.sendPaymentOutcome(paymentOutcome);
		}

		return ResponseEntity.status(HttpStatus.OK).body("Payment successful");
	}


}

