package com.wipro.FinGenieAI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.FinGenieAI.entity.Role;
import com.wipro.FinGenieAI.enums.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleType roleName);
}