package com.userservice.userservice.repository;

import com.userservice.userservice.model.Client;
import com.userservice.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
	Optional<Client> findClientByUser(User user);
}
