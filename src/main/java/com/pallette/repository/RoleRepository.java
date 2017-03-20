package com.pallette.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pallette.domain.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, String>{
	
	public Role findByName(String name);

}
