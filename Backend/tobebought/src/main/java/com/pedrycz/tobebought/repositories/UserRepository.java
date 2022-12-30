package com.pedrycz.tobebought.repositories;

import com.pedrycz.tobebought.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
