package com.ziorye.proofread.service;


import com.ziorye.proofread.dto.UserDto;
import com.ziorye.proofread.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    void updatePassword(User user);
}
