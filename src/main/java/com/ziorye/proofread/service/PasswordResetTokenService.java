package com.ziorye.proofread.service;


import com.ziorye.proofread.entity.PasswordResetToken;
import org.springframework.transaction.annotation.Transactional;

public interface PasswordResetTokenService {
    PasswordResetToken findFirstByTokenOrderByIdDesc(String token);
    PasswordResetToken save(PasswordResetToken passwordResetToken);

    PasswordResetToken findByToken(String token);

    @Transactional
    int expireThisToken(String token);
}
