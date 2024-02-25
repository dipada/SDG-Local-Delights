package com.payment.paymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_wallet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true)
	private String email;

	private long balance; // in cents euro

	public Wallet(String email) {
		this.email = email;
		this.balance = 10000; // for demo purposes
	}
}
