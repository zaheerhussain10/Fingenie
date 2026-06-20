package com.wipro.FinGenieAI.mapper;

import com.wipro.FinGenieAI.dto.UserDTO;
import com.wipro.FinGenieAI.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                
                // ✅ ADD THIS
                .password(user.getPassword())

                // ✅ CONVERT ROLE (Set<Role> → String)
                .role(user.getRoles().stream()
                        .map(role -> role.getRoleName().name())
                        .findFirst()
                        .orElse(null))
                .build();
    }

    public static User toEntity(UserDTO dto) {

        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .build();
    }
}