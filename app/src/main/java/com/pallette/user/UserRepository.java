package com.pallette.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long>{

	public User findByEmailAddress(final String name);

    public Optional<User> findById(final Long id);
}
