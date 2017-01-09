package com.pallette.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pallette.domain.Account;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.repository.AccountRepository;
import com.pallette.exception.AuthenticationException;

@Service
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	/**
	 * The accounts repository.
	 */
	@Autowired
	AccountRepository accounts;

	/**
	 * Retrieve an account with given id. The id here is the unique user id
	 * value of the account, ie the username.
	 * 
	 * @param id
	 *            The user id of the account.
	 * @return The account object if found or throws a NoRecordsFoundException.
	 */
	public Account findAccount(String username) {

		logger.debug("AccountService.findAccount: id=" + username);

		Account account = accounts.findByUsername(username);
		if (account == null) {
			logger.warn("AccountService.findAccount: could not find account with id: " + username);
			throw new NoRecordsFoundException();
		}

		logger.info(String.format("AccountService.findAccount - retrieved account with id: %s. Payload is: %s", username,
				account));

		return account;
	}

	/**
	 * Retrieves the account by the authorization token associated with that
	 * account and current login.
	 * 
	 * @param token
	 *            The token to search for.
	 * @return The account object if found or AuthenticationException otherwise.
	 */
	@Cacheable(value = "authorizationCache")
	public Account findAccountprofileByAuthtoken(String token) {
		logger.debug("AccountService.findAccountprofileByAuthtoken looking for authToken: " + token);
		if (token == null) {
			// TODO: no point in checking database. throw exception here.
			logger.error("AccountService.findAccountprofileByAuthtoken(): token is null");
			throw new AuthenticationException("Authorization Token is null");
		}
		Account accountProfile = null;
		accountProfile = accounts.findByAuthtoken(token);
		if (accountProfile == null) {
			logger.error("AccountService.findAccountprofileByAuthtoken(): accountProfile is null for token=" + token);
			throw new AuthenticationException("Authorization Token not found");
		}

		return accountProfile;
	}

	/**
	 * Saves the given account in the repository.
	 * 
	 * @param accountRequest
	 *            The account to save.
	 * @return the id of the account.
	 */
	public String saveAccount(Account accountRequest) {

		logger.debug("AccountService.saveAccount:" + accountRequest.toString());

		Account account = accounts.save(accountRequest);
		logger.info("AccountService.saveAccount: account saved: " + account);
		return account.getId();
	}

	/**
	 * Attempts to login the user with the given username and password. Throws
	 * AuthenticationException if an account with the given username and
	 * password cannot be found.
	 * 
	 * @param username
	 *            The username to login.
	 * @param password
	 *            The password to use.
	 * @return a map with the authtoken, account Id.
	 */
	public Map<String, Object> login(String username, String password) {
		logger.debug("login in user: " + username);
		Map<String, Object> loginResponse = null;
		Account account = accounts.findByUsernameAndPassword(username, password);
		if (account != null) {
			logger.debug("Found Account for user: " + username);
			account.setAuthtoken(UUID.randomUUID().toString());
			account = accounts.save(account); // persist new auth token and last
												// login
			loginResponse = new HashMap<String, Object>();

			loginResponse.put("authToken", account.getAuthtoken());
			loginResponse.put("accountid", account.getId());
			// loginResponse.put("password", account.getPasswd());

			logger.info("AccountService.login success for " + username + " username::token="
					+ loginResponse.get("authToken"));

		} else {
			logger.warn("AccountService.login failed to find username=" + username + " password=" + password);
			throw new AuthenticationException("Login failed for user: " + username);
		}
		return loginResponse;
	}

	/**
	 * logs the give user out of the system.
	 * 
	 * @param username
	 *            the username to logout.
	 * @return The account object or null;
	 */
	public Account logout(String username) {
		logger.debug("AccountService.logout: Logging out account with username: " + username);
		Account account = accounts.findByUsername(username);
		if (account != null) {
			account.setAuthtoken(null); // remove token
			accounts.save(account);
			logger.info("AccountService.logout: Account logged out: " + account.getUsername());
		} else {
			logger.warn("AccountService.logout: Could not find account to logout with userId: " + username);
		}
		return account;
	}

}
