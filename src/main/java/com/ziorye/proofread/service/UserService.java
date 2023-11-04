package com.ziorye.proofread.service;


import com.ziorye.proofread.dto.UserDto;
import com.ziorye.proofread.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    void updatePassword(User user);

    Page<User> findAll(int pageNumber, int pageSize);
}
