package com.pallette.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pallette.domain.Address;

public interface UserAddressRepository extends MongoRepository<Address, String>{

	public List<Address> findByProfileId(final String profileId);

    public Optional<Address> findById(final String id);
    
    public Address findByProfileIdAndId(final String profileId, String id);
}
