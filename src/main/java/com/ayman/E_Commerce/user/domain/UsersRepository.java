package com.ayman.E_Commerce.user.domain;

import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.user.infrastructure.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends BaseRepository<User, Long> {
    Optional<User> findByName(String username);
}
