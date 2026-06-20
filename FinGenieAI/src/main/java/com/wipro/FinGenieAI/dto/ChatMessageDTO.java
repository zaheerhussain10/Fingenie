package com.wipro.FinGenieAI.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {

    private Long id;

    @NotBlank(message = "Message cannot be empty")
    private String message;

    private String response;

    private LocalDateTime timestamp;
}