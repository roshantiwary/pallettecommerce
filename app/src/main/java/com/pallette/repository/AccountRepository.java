package com.pallette.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pallette.domain.Account;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

	public Account findByUsernameAndPassword(String username, String password);
	public Account findByUsername(String userId);
	public Account findByAuthtoken(String authtoken);
//	public Account findOne(Long profileId);
}

