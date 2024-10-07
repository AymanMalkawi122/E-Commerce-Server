package com.ayman.E_Commerce.core;

import com.ayman.E_Commerce.core.exceptions.RepositoryException;
import com.ayman.E_Commerce.user.infrastructure.User;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UtilMethods {
    public static void throwIfNotOwner(User user, Long id) {
        if (!Objects.equals(id, user.getId()) && user.getId() != null && id != null) {
            throw new RepositoryException(BaseRepository.dataMustBeOwnedByTheUserMessage(), HttpStatus.FORBIDDEN);
        }
    }

    public static void throwIfNotAdmin(User user) {
        if (!user.isAdmin()) {
            throw new RepositoryException(BaseRepository.dataMustBeOwnedByTheUserMessage(), HttpStatus.FORBIDDEN);
        }
    }

    public static void throwIfNotOwnerOrAdmin(User user, Long id) {
        if (!user.isAdmin() && !Objects.equals(id, user.getId()) && user.getId() != null && id != null) {
            throw new RepositoryException(BaseRepository.dataMustBeOwnedByTheUserMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
