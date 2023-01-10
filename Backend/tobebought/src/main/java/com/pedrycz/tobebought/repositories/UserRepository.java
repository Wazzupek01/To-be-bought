package com.pedrycz.tobebought.repositories;

import com.pedrycz.tobebought.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
}
