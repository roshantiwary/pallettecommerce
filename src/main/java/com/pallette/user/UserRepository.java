package com.pallette.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{

	public User findByEmailAddress(final String name);

    public User findById(final String id);
}
