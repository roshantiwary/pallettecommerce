package com.pallette.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pallette.beans.AccountBean;
import com.pallette.beans.AccountResponse;
import com.pallette.beans.AddressBean;
import com.pallette.beans.AddressResponseBean;
import com.pallette.beans.PasswordBean;
import com.pallette.domain.Account;
import com.pallette.domain.AuthenticationRequest;
import com.pallette.repository.RoleRepository;
import com.pallette.response.AddressResponse;
import com.pallette.response.Response;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	public AccountResponse createAccount(AccountBean account) throws IllegalAccessException, InvocationTargetException {
		logger.debug("Saving account with userId: " + account.getUsername());
		AccountResponse genericResponse = null;
		genericResponse = accountService.saveAccount(account);
		
		return genericResponse;
	}

	/**
	 * This Method updates the user's personal
	 * details
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	public AccountResponse updateProfile(AccountBean account) throws Exception {
		AccountResponse genericResponse = null;
		genericResponse = accountService.updateProfile(account);
		return genericResponse;
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
	
	public Account getAccountByProfileId(String profileId) {
		logger.debug("Looking for account with profile id: " + profileId);

		Account account = accountService.findAccountById(profileId);
		logger.debug("Got Account: " + account);
		return account;
	}

	public void logout(String user) {
		logger.debug("logging out account with userId: " + user);
		
		accountService.logout(user);
	}
	
	
	/**
	 * This method adds the new address in user profile
	 * 
	 * @param address
	 * @return
	 * @throws Exception 
	 */
	public AddressResponse addNewAddress(AddressBean address) throws Exception {
		AddressResponse genericResponse = null;
		genericResponse = accountService.addNewAddress(address);
		return genericResponse;
	}

	/**
	 * This method updates the address details 
	 * in user profile
	 * 
	 * @param addressKey
	 * @param address
	 * @return
	 * @throws Exception 
	 */
	public AddressResponse editAddress(String addressKey,AddressBean address) throws Exception {
		AddressResponse genericResponse = null;
		genericResponse = accountService.editAddress(addressKey,address);
		return genericResponse;
	}

	/**
	 * This method removes the address from address repository
	 * and user profile
	 * 
	 * @param addressKey
	 * @return
	 * @throws Exception 
	 */
	public Response removeAddress(String addressKey) throws Exception {
		Response genericResponse = null;
		genericResponse = accountService.removeAddress(addressKey);
		return genericResponse;
	}

	/**
	 * This method updates the password
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Response changePassword(PasswordBean password) throws Exception {
		Response genericResponse = null;
		genericResponse = accountService.changePassword(password);
		return genericResponse;
	}

	public AddressResponseBean getAllProfileAddress(String profileId) {
		AddressResponseBean genericResponse = null;
		genericResponse = accountService.getAllProfileAddress(profileId);
		return genericResponse;
	}
}
