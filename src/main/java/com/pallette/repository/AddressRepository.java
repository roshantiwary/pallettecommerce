package com.pallette.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pallette.domain.Address;

public interface AddressRepository extends PagingAndSortingRepository<Address, String>{

	public Address findOne(String id);
	
	public List<Address> findAddressByProfileId(String profileId);
}
