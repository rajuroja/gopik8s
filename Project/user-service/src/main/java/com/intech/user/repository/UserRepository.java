package com.intech.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.intech.user.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
