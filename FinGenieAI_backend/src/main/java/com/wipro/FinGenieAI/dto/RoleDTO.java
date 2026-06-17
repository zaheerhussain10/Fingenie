package com.wipro.FinGenieAI.dto;


import com.wipro.FinGenieAI.enums.RoleType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {

    private Long id;

    @NotNull(message = "Role is required")
    private RoleType roleName;
}
