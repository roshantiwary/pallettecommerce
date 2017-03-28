package com.pallette.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pallette.beans.AccountBean;
import com.pallette.beans.AddressBean;
import com.pallette.commerce.contants.CommerceContants;
import com.pallette.domain.Account;
import com.pallette.domain.Address;
import com.pallette.domain.Role;
import com.pallette.exception.AuthenticationException;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.repository.AccountRepository;
import com.pallette.repository.AddressRepository;
import com.pallette.repository.RoleRepository;
import com.pallette.response.GenericResponse;

@Service
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	/**
	 * The accounts repository.
	 */
	@Autowired
	AccountRepository accounts;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AddressRepository addresses;
	
	@Autowired
	private MongoOperations mongoOperation; 


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
			//throw new NoRecordsFoundException();
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
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws Exception 
	 */
	public GenericResponse saveAccount(AccountBean accountBean) throws NoRecordsFoundException, IllegalAccessException, InvocationTargetException {

		logger.debug("AccountService.saveAccount:" + accountBean.toString());
		
		if(StringUtils.isEmpty(accountBean.getPassword()) && StringUtils.isEmpty(accountBean.getConfirmPassword())){
			logger.error("Please provide passowrd and confirm password");
			throw new NoRecordsFoundException("Please provide passowrd and confirm password");
		}
		
		if(!accountBean.getPassword().equalsIgnoreCase(accountBean.getConfirmPassword())){
			logger.error("Password and Confirm Password does not match");
			throw new NoRecordsFoundException("Password and Confirm Password does not match");
		}
		
		Account accountItem = new Account();
		Account account = null;
		BeanUtils.copyProperties(accountItem, accountBean);
		
		
		GenericResponse accountResponse = new GenericResponse();
		List<Account> accountsList = new ArrayList<Account>();
		
		if(null != accounts.findByUsername(accountItem.getUsername())){
			logger.info("Account is already created for username " + accountItem.getUsername());
			account = accounts.findByUsername(accountItem.getUsername());
			accountsList.add(account);
			accountResponse.setItems(accountsList);
			accountResponse.setItemCount(accountsList.size());
			accountResponse.setStatusCode(HttpStatus.ALREADY_REPORTED.value());
			accountResponse.setMessage("Account has been already created for username : "+ accountItem.getUsername());
			return accountResponse;
		}
		accountItem.setPassword(passwordEncoder.encode(accountItem.getPassword()));
		accountItem.setCreationdate(new Date());
		
		// Create User Role
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(userRole);
		accountItem.addRole(roles);
		
		account = accounts.save(accountItem);
		
		if(null != account){
			logger.info("AccountService.saveAccount: account saved: " + account);
			accountsList.add(account);
			accountResponse.setItems(accountsList);
			accountResponse.setItemCount(accountsList.size());
			accountResponse.setStatusCode(HttpStatus.OK.value());
			accountResponse.setMessage("Account has been created");
		}else{
			logger.info("Account has not been created for ID " + accountItem.getId());
			accountsList.add(account);
			accountResponse.setItems(accountsList);
			accountResponse.setItemCount(accountsList.size());
			accountResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			accountResponse.setMessage("Account has been not been created due to some internal server error");
		}
		return accountResponse;
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
		Account account = accounts.findByUsername(username);
		if (account != null) {
			
			if(!passwordEncoder.matches(password, account.getPassword())){
				logger.debug("Incorrect Password for user : " + username);
				return loginResponse;
			}
			logger.debug("Found Account for user: " + username);
			account.setAuthtoken(UUID.randomUUID().toString());
			account = accounts.save(account); // persist new auth token and last
												// login
			loginResponse = new HashMap<String, Object>();

			loginResponse.put("authToken", account.getAuthtoken());
			loginResponse.put("accountid", account.getId());
			loginResponse.put("role", account.getRoles());
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

	
	/**
	 * This method Updates the user's personal details
	 * 
	 * @param account
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public GenericResponse updateProfile(AccountBean accountBean) throws IllegalAccessException, InvocationTargetException {
		logger.debug("AccountService.updateProfile: updating profile with id : " + accountBean.getId());
		GenericResponse genericResponse = new GenericResponse();
		Account accountItem = new Account();
		BeanUtils.copyProperties(accountItem, accountBean);
		Account accountForUpdate = accounts.findOne(accountItem.getId());
		List<Account> accountList = new ArrayList<Account>();
		if (accountForUpdate != null) {
			accountForUpdate.setId(accountItem.getId());
			accountForUpdate.setFirstName(accountItem.getFirstName());
			accountForUpdate.setLastName(accountItem.getLastName());
			accountForUpdate.setPhoneNumber(accountItem.getPhoneNumber());
			accountForUpdate.setUsername(accountItem.getUsername());
			accountForUpdate.setAuthtoken(accountItem.getAuthtoken());
			accountList.add(accounts.save(accountForUpdate));
			genericResponse.setItems(accountList);
			genericResponse.setItemCount(accountList.size());
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage("User Profile has been updated for id : "+accountForUpdate.getId());
			return genericResponse;
		} else {
			genericResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			genericResponse.setMessage("User Not Found For User Id");
			return genericResponse;
		}
	}
	
	/**
	 * This method add new address in user profile
	 * 
	 * @param addressBean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes" })
	public GenericResponse addNewAddress(AddressBean addressBean) throws IllegalAccessException, InvocationTargetException {
		logger.debug("AccountService.addNewAddress: Adding New Address to the profile : ");
		
		GenericResponse genericResponse = new GenericResponse();
		Address addressItem = new Address();
		BeanUtils.copyProperties(addressItem, addressBean);
		Account account = accounts.findOne(addressItem.getOwnerId());
		Address address = addresses.save(addressItem);
		List<Address> addressList = new ArrayList<Address>();
		Map<Object, Collection> accountMap = new HashMap<Object, Collection>();
		if(null != address && null != account){
			logger.debug("AccountService.addNewAddress: New Address Added To The Profile :   with address ID : "+address.getId());
			addressList.add(address);
			account.getAddresses().add(address);
			account = accounts.save(account);
			accountMap.put(account, account.getAddresses());
			genericResponse.setItemCount(addressList.size());
			genericResponse.setItemMapData(accountMap);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage("Address Added Successfulley");
		}else{
			logger.debug("AccountService.addNewAddress: There is Some Error While Adding New Address To The Profile ");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			genericResponse.setMessage("Address not added in the profile");
		}
		return genericResponse;
	}

	/**
	 * This method edits the address details in user profile
	 * 
	 * @param addressKey
	 * @param addressBean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public GenericResponse editAddress(String addressKey, AddressBean addressBean) throws IllegalAccessException, InvocationTargetException {
		logger.debug("AccountService.editAddress: Editing Address");
		
		GenericResponse genericResponse = new GenericResponse();
		Address addressItem = addresses.findOne(addressKey);
		BeanUtils.copyProperties(addressItem, addressBean);
		Account account = accounts.findOne(addressItem.getOwnerId());
		
		Map<Object, Collection> accountMap = new HashMap<Object, Collection>();
		if(null != addressItem && null != account){
			logger.debug("AccountService.editAddress: Edited Address with address : "+addressItem.getId());
			addressItem = addresses.save(addressItem);
			accountMap.put(account, account.getAddresses());
			genericResponse.setItemCount(account.getAddresses().size());
			genericResponse.setItemMapData(accountMap);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage("Address Updated Successfulley");
		}else{
			logger.debug("AccountService.editAddress: There is Some Error While Editing Address");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			genericResponse.setMessage("Address not updated");
		}
		return genericResponse;
	}

	/**
	 * This method removes the address from address repository 
	 * and user profile
	 * 
	 * @param addressKey
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public GenericResponse removeAddress(String addressKey) {
		logger.debug("AccountService.removeAddress: Removing Address : "+addressKey);
		
		GenericResponse genericResponse = new GenericResponse();
		Address addressItem = addresses.findOne(addressKey);
		Account account = accounts.findOne(addressItem.getOwnerId());
		Query addressRemovalQuery = new Query();
		addressRemovalQuery.addCriteria(Criteria.where(CommerceContants._ID).is(addressItem.getId()));
		Address address = mongoOperation.findAndRemove(addressRemovalQuery , Address.class);
		account.removeAddress(addressItem);
		accounts.save(account);
		logger.debug("Address Document Removed Successfully :" , address.getId());
		Map<Object, Collection> accountMap = new HashMap<Object, Collection>();
		if(null != addressItem && null != account){
			logger.debug("AccountService.removeAddress: Removed Address From Profile : "+addressKey);
			accountMap.put(account, account.getAddresses());
			genericResponse.setItemCount(account.getAddresses().size());
			genericResponse.setItemMapData(accountMap);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage("Address Removed Succesfully");
		}else{
			logger.debug("AccountService.removeAddress: There is Some Error While removing Address");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			genericResponse.setMessage("Some Error Occured While Removing Address");
		}
		return genericResponse;
	}
}
