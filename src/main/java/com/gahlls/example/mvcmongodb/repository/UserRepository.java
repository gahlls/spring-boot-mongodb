package com.gahlls.example.mvcmongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gahlls.example.mvcmongodb.model.User;

public interface UserRepository extends MongoRepository<User, String>{

	Optional<User> findByEmail(final String email);
}
