package com.ziorye.proofread.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PasswordResetEmailDto {
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
}
