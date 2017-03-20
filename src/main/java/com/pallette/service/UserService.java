package com.pallette.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pallette.domain.AuthenticationRequest;
import com.pallette.domain.Role;
import com.pallette.repository.RoleRepository;
import com.pallette.domain.Account;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	public void createAccount(Account account) {
		logger.debug("Saving account with userId: " + account.getUsername());
		
		// Create User Role
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(userRole);
		account.addRole(roles);
				
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountService.saveAccount(account);
	}

	public Map<String, Object> login(AuthenticationRequest request) {
		logger.debug("logging in with userId:" + request.getUsername());

		Map<String, Object> result = accountService.login(request.getUsername(), request.getPassword());
		return result;
	}

	// TODO: change to /account/{user}
	public Account getAccount(String user) {
		logger.debug("Looking for account with userId: " + user);

		Account account = accountService.findAccount(user);
		logger.debug("Got Account: " + account);
		return account;
	}

	public void logout(String user) {
		logger.debug("logging out account with userId: " + user);
		
		accountService.logout(user);
	}
}
