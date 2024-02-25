package com.authentication.authenticationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String picture;
	private String phoneNumber;
	private String shippingAddress;
	private Boolean googleAccount;

}
