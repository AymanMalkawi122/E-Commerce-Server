package com.ayman.E_Commerce.user.infrastructure;

import com.ayman.E_Commerce.core.ResponseState;

import java.util.Optional;

public interface UsersService {
    ResponseState<User> createUser(User user);
    ResponseState<Optional<User>> getUserById(Long id, boolean throwIfNull);
    ResponseState<User> loadUserByUsername(String username);
    ResponseState<User> updateUser(User user);
    ResponseState<String> deleteUserById(Long id);
}
