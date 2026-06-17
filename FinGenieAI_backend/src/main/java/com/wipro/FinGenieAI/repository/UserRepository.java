package com.wipro.FinGenieAI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.FinGenieAI.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}