package com.ayman.E_Commerce.core;

import com.ayman.E_Commerce.core.exceptions.RepositoryException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@NoRepositoryBean
public interface BaseRepository<E, ID> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
    String missingIdMessage = "id must provided";
    String successfulDeletionMessage = "Successfully deleted";

    default E ifExistsElseThrow(ID id, Supplier<E> block) {
        if (id == null) {
            throw new RepositoryException(missingIdMessage, HttpStatus.BAD_REQUEST);
        }
        if (existsById(id)) {
            return block.get();
        }
        throw new EntityNotFoundException(entityNotFoundMessage(id.toString()));
    }

    default void ifExistsElseThrow(ID id, Runnable block) {
        if (id == null) {
            throw new RepositoryException(missingIdMessage, HttpStatus.BAD_REQUEST);
        }
        if (existsById(id)) {
            block.run();
            return;
        }
        throw new EntityNotFoundException(entityNotFoundMessage(id.toString()));
    }

    static String entityNotFoundMessage(String id) {
        return "Entity not found with id " + id;
    }

    static String entityNotFoundMessage(String id, String entityName) {
        return entityName + " not found with id " + id;
    }
}
