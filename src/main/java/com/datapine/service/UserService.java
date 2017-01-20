package com.datapine.service;

import com.datapine.domain.User;

public interface UserService {

    User register(String email, String password);

    User updatePassword(Long userId, String oldPassword, String newPassword);

    Iterable<User> findAllUsers();

    User findUser(Long userId);

    User findByEmail(String email);

    void deleteUser(Long userId);

    User save(User user);

}
