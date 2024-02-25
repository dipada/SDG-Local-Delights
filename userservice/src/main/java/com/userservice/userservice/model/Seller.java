package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "_seller")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_email", referencedColumnName = "email")
	private User user;

	private String vat;

	public Seller(User newUser, String vatNumber) {
		this.user = newUser;
		this.vat = vatNumber;
	}
}
