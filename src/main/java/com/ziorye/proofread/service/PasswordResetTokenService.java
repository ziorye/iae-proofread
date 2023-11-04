package com.ziorye.proofread.service;


import com.ziorye.proofread.entity.PasswordResetToken;

public interface PasswordResetTokenService {
    PasswordResetToken findFirstByTokenOrderByIdDesc(String token);
    PasswordResetToken save(PasswordResetToken passwordResetToken);

    PasswordResetToken findByToken(String token);
}
