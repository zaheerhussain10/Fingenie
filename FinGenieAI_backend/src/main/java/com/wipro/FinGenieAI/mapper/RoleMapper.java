package com.wipro.FinGenieAI.mapper;

import com.wipro.FinGenieAI.dto.RoleDTO;
import com.wipro.FinGenieAI.entity.Role;

public class RoleMapper {

    public static RoleDTO toDTO(Role role) {
        if (role == null) return null;

        return RoleDTO.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static Role toEntity(RoleDTO dto) {
        if (dto == null) return null;

        return Role.builder()
                .id(dto.getId())
                .roleName(dto.getRoleName())
                .build();
    }
}