package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_email", referencedColumnName = "email")
	private User user;

	private String shippingAddress;

	public Client(User newUser, String shippingAddress) {
		this.user = newUser;
		this.shippingAddress = shippingAddress;
	}
}
