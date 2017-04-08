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
import com.pallette.beans.PasswordBean;
import com.pallette.beans.ProfileAddressBean;
import com.pallette.beans.ProfileAddressResponse;
import com.pallette.beans.ProfileAddressResponseBean;
import com.pallette.domain.Account;
import com.pallette.domain.AuthenticationRequest;
import com.pallette.repository.RoleRepository;
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
	 * @param profileId 
	 * @return
	 * @throws Exception 
	 */
	public AccountResponse updateProfile(AccountBean account, String profileId) throws Exception {
		AccountResponse genericResponse = null;
		genericResponse = accountService.updateProfile(account,profileId);
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
	 * This method Get the address 
	 * in user profile
	 * 
	 * @param addressKey
	 * @param profileId 
	 * @return
	 * @throws Exception 
	 */
	public ProfileAddressResponse getAddress(String addressKey, String profileId) throws Exception {
		ProfileAddressResponse genericResponse = null;
		genericResponse = accountService.getAddress(addressKey,profileId);
		return genericResponse;
	}
	
	/**
	 * This method adds the new address in user profile
	 * 
	 * @param address
	 * @param profileId 
	 * @return
	 * @throws Exception 
	 */
	public ProfileAddressResponse addNewAddress(ProfileAddressBean address, String profileId) throws Exception {
		ProfileAddressResponse genericResponse = null;
		genericResponse = accountService.addNewAddress(address,profileId);
		return genericResponse;
	}

	/**
	 * This method updates the address details 
	 * in user profile
	 * 
	 * @param addressKey
	 * @param address
	 * @param profileId 
	 * @return
	 * @throws Exception 
	 */
	public ProfileAddressResponse editAddress(String addressKey,ProfileAddressBean address, String profileId) throws Exception {
		ProfileAddressResponse genericResponse = null;
		genericResponse = accountService.editAddress(addressKey,address,profileId);
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
	 * @param profileId 
	 * @return
	 * @throws Exception
	 */
	public Response changePassword(PasswordBean password, String profileId) throws Exception {
		Response genericResponse = null;
		genericResponse = accountService.changePassword(password,profileId);
		return genericResponse;
	}

	public ProfileAddressResponseBean getAllProfileAddress(String profileId) {
		ProfileAddressResponseBean genericResponse = null;
		genericResponse = accountService.getAllProfileAddress(profileId);
		return genericResponse;
	}
}
