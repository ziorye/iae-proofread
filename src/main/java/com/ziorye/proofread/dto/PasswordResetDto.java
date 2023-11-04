package com.ziorye.proofread.dto;

import com.ziorye.proofread.validator.PasswordConfirmation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordConfirmation(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "两次密码输入不一致"
)
public class PasswordResetDto {
    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;

    @NotEmpty
    @Size(min = 6, max = 20)
    private String confirmPassword;

    @NotEmpty
    private String token;
}
