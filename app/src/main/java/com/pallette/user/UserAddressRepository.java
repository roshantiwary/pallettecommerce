package com.pallette.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pallette.domain.Address;

public interface UserAddressRepository extends MongoRepository<Address, Long>{

	public List<Address> findByProfileId(final Long profileId);

    public Optional<Address> findById(final Long id);
    
    public Address findByProfileIdAndId(final Long profileId, Long id);
}
