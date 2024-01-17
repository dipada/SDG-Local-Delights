package com.userservice.userservice.repository;

import com.userservice.userservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findUserByEmail(String email);

    void deleteUserByEmail(String email);
}
