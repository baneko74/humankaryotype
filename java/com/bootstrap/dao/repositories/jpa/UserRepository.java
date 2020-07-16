package com.bootstrap.dao.repositories.jpa;

import org.springframework.data.repository.CrudRepository;

import com.bootstrap.dao.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByUsername(String username);

}
