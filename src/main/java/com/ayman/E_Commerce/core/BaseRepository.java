package com.ayman.E_Commerce.core;

import com.ayman.E_Commerce.core.exceptions.RepositoryException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;

import java.util.function.Function;
import java.util.function.Supplier;

@NoRepositoryBean
public interface BaseRepository<E, ID> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
    String missingIdMessage = "id must provided";
    String successfulDeletionMessage = "Successfully deleted";
    default String entityName() {return "Entity";}


    default E ifExistsElseThrow(ID id, Function<E, E> block) {
        if (id == null) {
            throw new RepositoryException(missingIdMessage, HttpStatus.BAD_REQUEST);
        }
        if (existsById(id)) {
            return block.apply(findById(id).get());
        }
        throw new EntityNotFoundException(entityNotFoundMessage(id.toString()));
    }

    default E ifExistsElseThrow(ID id, Supplier<E> block) {
        if (id == null) {
            throw new RepositoryException(missingIdMessage, HttpStatus.BAD_REQUEST);
        }
        if (existsById(id)) {
            return block.get();
        }
        throw new EntityNotFoundException(entityNotFoundMessage(id.toString()));
    }

    default E ifIsNewElseThrow(ID id, Supplier<E> block) {
        if (id == null) {
            throw new RepositoryException(missingIdMessage, HttpStatus.BAD_REQUEST);
        }
        if (!existsById(id)) {
            return block.get();
        }
        throw new RepositoryException(entityMustBeNewMessage(id.toString()), HttpStatus.BAD_REQUEST);
    }

    default String entityNotFoundMessage(String id) {
        return entityName() + " not found with id " + id;
    }

     default String entityMustBeNewMessage(String id) {
        return entityName() + " with " + id + "already exists";
    }

    default String entityIdIsImmutableMessage() {
        return entityName() + " id must remain the same";
    }

    static String dataMustBeOwnedByTheUserMessage() {
        return "data must be owned by the User";
    }
}
