package com.ayman.E_Commerce.user.domain;

import com.ayman.E_Commerce.core.BaseRepository;
import com.ayman.E_Commerce.core.ResponseState;
import com.ayman.E_Commerce.core.exceptions.RepositoryException;
import com.ayman.E_Commerce.user.infrastructure.UsersService;
import com.ayman.E_Commerce.user.infrastructure.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public ResponseState<User> createUser(User user) {
        User result = usersRepository.ifIsNewElseThrow(user.getId(), () -> usersRepository.save(user));
        return new ResponseState<>(result, HttpStatus.CREATED);
    }

    @Override
    public ResponseState<Optional<User>> getUserById(Long id, boolean throwIfNull) {
        final Optional<User> result = usersRepository.findById(id);
        if (result.isEmpty() && throwIfNull) {
            throw new EntityNotFoundException(usersRepository.entityNotFoundMessage(id.toString()));
        }
        return new ResponseState<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseState<User> loadUserByUsername(String username) {
        return null;
    }

    @Override
    public ResponseState<User> updateUser(User newUser) {
        final Optional<User> user = usersRepository.findById(newUser.getId());

        if (user.isEmpty()) {
            throw new EntityNotFoundException(usersRepository.entityNotFoundMessage(newUser.getId().toString()));
        }
        if (!Objects.equals(user.get().getId(), newUser.getId())) {
            throw new RepositoryException(usersRepository.entityIdIsImmutableMessage(), HttpStatus.BAD_REQUEST);
        }
        // TODO validate change by owner or admin

        return new ResponseState<>(usersRepository.save(newUser), HttpStatus.OK);
    }

    // TODO implement get user privileges

    @Override
    public ResponseState<String> deleteUserById(Long id) {
        // TODO validate change by owner or admin
        usersRepository.deleteById(id);
        return new ResponseState<>(BaseRepository.successfulDeletionMessage, null, HttpStatus.NO_CONTENT);
    }
}
