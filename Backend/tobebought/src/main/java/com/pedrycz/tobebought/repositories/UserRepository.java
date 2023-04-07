package com.pedrycz.tobebought.repositories;

import com.pedrycz.tobebought.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    User findByEmail(String email);
}
