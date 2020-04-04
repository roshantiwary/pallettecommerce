package com.pallette.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.pallette.beans.AccountResponse;
import com.pallette.beans.AddressBean;
import com.pallette.beans.PasswordBean;
import com.pallette.beans.ProfileAddressBean;
import com.pallette.beans.ProfileAddressResponse;
import com.pallette.beans.ProfileAddressResponseBean;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.constants.SequenceConstants;
import com.pallette.domain.Account;
import com.pallette.domain.Address;
import com.pallette.domain.Role;
import com.pallette.exception.AuthenticationException;
import com.pallette.repository.AccountRepository;
import com.pallette.repository.AddressRepository;
import com.pallette.repository.RoleRepository;
import com.pallette.repository.SequenceDao;
import com.pallette.response.Response;

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
	AddressRepository addressRepository;
	
	@Autowired
	private MongoOperations mongoOperation; 
	
	@Autowired
	private SequenceDao sequenceDao;

	/**
	 * Retrieve an account with Profile/Account id.
	 * 
	 * @param profileId
	 *            The profile id of the account.
	 * @return The account object if found or throws a NoRecordsFoundException.
	 */
	public Account findAccountById(Long profileId) {

		logger.debug("AccountService.findAccount: id=" + profileId);

		Optional<Account> account = accounts.findById(profileId);
		if (!account.isPresent()) {
			logger.warn("AccountService.findAccount: could not find account with id: " + profileId);
			//throw new NoRecordsFoundException();
		}

		logger.info(String.format("AccountService.findAccount - retrieved account with id: %s. Payload is: %s", profileId,
				account));

		return account.get();
	}

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
	public AccountResponse saveAccount(AccountBean accountBean) throws IllegalAccessException, InvocationTargetException {

		logger.debug("AccountService.saveAccount:" + accountBean.toString());
		AccountResponse accountResponse = new AccountResponse();
		String profileId = null;
		if(!accountBean.getPassword().equalsIgnoreCase(accountBean.getConfirmPassword())){
			logger.error("Password and Confirm Password does not match");
			throw new IllegalArgumentException("Password and Confirm Password does not match");
		}
		if(StringUtils.isEmpty(accountBean.getId())){
			profileId = sequenceDao.getNextProfileSequenceId(SequenceConstants.SEQ_KEY);
			accountBean.setId(profileId);
		}
		
		Account accountItem = new Account();
		Account account = null;
		BeanUtils.copyProperties(accountItem, accountBean);
		
		if(null != accounts.findByUsername(accountItem.getUsername())){
			logger.info("Account is already created for username " + accountItem.getUsername());
			account = accounts.findByUsername(accountItem.getUsername());
			accountResponse.setId(account.getId());
			accountResponse.setFirstName(account.getFirstName());
			accountResponse.setLastName(account.getLastName());
			accountResponse.setEmail(account.getUsername());
			accountResponse.setAddress(account.getAddresses());
			accountResponse.setPhoneNumber(account.getPhoneNumber());
			accountResponse.setStatusCode(HttpStatus.ALREADY_REPORTED.value());
			accountResponse.setMessage("Account has been already created for username : "+ accountItem.getUsername());
			accountResponse.setStatus(Boolean.FALSE);
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
			accountResponse.setId(account.getId());
			accountResponse.setFirstName(account.getFirstName());
			accountResponse.setLastName(account.getLastName());
			accountResponse.setEmail(account.getUsername());
			accountResponse.setAddress(account.getAddresses());
			accountResponse.setPhoneNumber(account.getPhoneNumber());
			accountResponse.setStatusCode(HttpStatus.OK.value());
			accountResponse.setMessage("Account has been created");
			accountResponse.setStatus(Boolean.TRUE);
		}else{
			logger.info("Account has not been created for ID " + accountItem.getId());
			throw new IllegalArgumentException("Account has been not been created due to some internal server error");
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
	 * @param profileId 
	 * 
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	public AccountResponse updateProfile(AccountBean accountBean, Long profileId) throws Exception {
		logger.debug("AccountService.updateProfile: updating profile with id : " + profileId);
		if(StringUtils.isEmpty(profileId))
			throw new IllegalArgumentException("Please provide Profile Id for update");
		Account accountItem = new Account();
		BeanUtils.copyProperties(accountItem, accountBean);
		accountItem.setId(profileId);
		Optional<Account> accountForUpdate = accounts.findById(accountItem.getId());
		Account account = null;
		if (accountForUpdate.isPresent()) {
			accountForUpdate.get().setId(accountItem.getId());
			accountForUpdate.get().setFirstName(accountItem.getFirstName());
			accountForUpdate.get().setLastName(accountItem.getLastName());
			accountForUpdate.get().setPhoneNumber(accountItem.getPhoneNumber());
			accountForUpdate.get().setUsername(accountItem.getUsername());
			accountForUpdate.get().setAuthtoken(accountItem.getAuthtoken());
			account = accounts.save(accountForUpdate.get());		} else {
			throw new Exception("User Not Found for Id"+accountItem.getId());
		}
		return constructResponse(account);
	}
	
	private AccountResponse constructResponse(Account account) {
		AccountResponse response = new AccountResponse();
		if(null != account){
			response.setEmail(account.getUsername());
			response.setFirstName(account.getFirstName());
			response.setLastName(account.getLastName());
			response.setId(account.getId());
			response.setPhoneNumber(account.getPhoneNumber());
			response.setStatus(Boolean.TRUE);
			response.setMessage("Profile Details");
			response.setStatusCode(HttpStatus.OK.value());
		}
		return response;
	}

	/**
	 * This method add new address in user profile
	 * 
	 * @param profileAddressBean
	 * @param profileId 
	 * @return
	 * @throws Exception 
	 */
	public ProfileAddressResponse addNewAddress(ProfileAddressBean profileAddressBean, Long profileId) throws Exception {
		
		logger.debug("AccountService.addNewAddress: Adding New Address to the profile : ");
		ProfileAddressResponse genericResponse = new ProfileAddressResponse();
		Address addressItem = new Address();
		
		BeanUtils.copyProperties(addressItem, profileAddressBean);
		addressItem.setProfileId(profileId);
		Optional<Account> account = accounts.findById(profileId);
		Address address = addressRepository.save(addressItem);
		if(null != address && account.isPresent()){
			logger.debug("AccountService.addNewAddress: New Address Added To The Profile :   with address ID : "+ address.getId());
			account.get().getAddresses().add(address);
			accounts.save(account.get());
			
			BeanUtils.copyProperties(genericResponse , addressItem);
			genericResponse.setStatus(Boolean.TRUE);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage("Address Added Successfulley");
			genericResponse.setId(address.getId());
		}else{
			logger.error("AccountService.addNewAddress: There is Some Error While Adding New Address To The Profile ");
			throw new Exception("There is Some Error While Adding New Address To The Profile");
		}
		return genericResponse;
	}

	/**
	 * This method returns address in user profile
	 * 
	 * @param addressKey
	 * @param profileId 
	 * @return
	 * @throws Exception 
	 */	
	public ProfileAddressResponse getAddress(Long addressKey, Long profileId) throws Exception {
		logger.debug("AccountService.getAddress: Get Address" + addressKey + "from profile " + profileId);
		ProfileAddressResponse genericResponse = new ProfileAddressResponse();
		Optional<Address> addressItem = addressRepository.findById(addressKey);
		if (addressItem.isPresent()) {
			Address address = addressItem.get();
			if (null != address.getProfileId() && address.getProfileId().equals(profileId)) {
				
				logger.debug("AccountService.getAddress: Address found in Profile :   with address ID : " + address.getId());
				
				genericResponse.setStatus(Boolean.TRUE);
				genericResponse.setStatusCode(HttpStatus.OK.value());
				genericResponse.setMessage("Address Retreived Successfulley");
				genericResponse.setId(address.getId());
				genericResponse.setFirstName(address.getFirstName());
				genericResponse.setLastName(address.getLastName());
				genericResponse.setAddress1(address.getAddress1());
				genericResponse.setAddress2(address.getAddress2());
				genericResponse.setCity(address.getCity());
				genericResponse.setState(address.getState());
				genericResponse.setPhoneNumber(address.getPhoneNumber());
			}
		} else{
			logger.error("AccountService.getAddress: Address not found in Profile");
			throw new Exception("Address not found in Profile");
		}
		return genericResponse;
	}
	
	/**
	 * This method edits the address details in user profile
	 * 
	 * @param addressKey
	 * @param address
	 * @param profileId 
	 * @return
	 * @throws Exception 
	 */
	public ProfileAddressResponse editAddress(Long addressKey, ProfileAddressBean address, Long profileId) throws Exception {
		logger.debug("AccountService.editAddress: Editing Address");
		
		ProfileAddressResponse genericResponse = new ProfileAddressResponse();
		Optional<Address> addressItem = addressRepository.findById(addressKey);
		BeanUtils.copyProperties(addressItem, address);
		addressItem.get().setProfileId(profileId);
		Optional<Account> account = accounts.findById(profileId);

		if (addressItem.isPresent() && account.isPresent()) {
			logger.debug("AccountService.editAddress: Edited Address with address : " + addressItem.get().getId());
			addressRepository.save(addressItem.get());
			genericResponse.setStatus(Boolean.TRUE);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage("Address Updated Successfulley");
			genericResponse.setId(addressItem.get().getId());
			BeanUtils.copyProperties(genericResponse, addressItem.get());
		} else {
			logger.error("AccountService.editAddress: There is Some Error While Editing Address");
			throw new Exception("There is Some Error While Updating Address");
		}
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
	public Response removeAddress(Long addressKey) throws Exception {
		logger.debug("AccountService.removeAddress: Removing Address : "+addressKey);
		
		Response genericResponse = new Response();
		if(StringUtils.isEmpty(addressKey))
			throw new IllegalArgumentException("Please provide address key");
		
		Optional<Address> addressItem = addressRepository.findById(addressKey);
		Optional<Account> account = accounts.findById(addressItem.get().getProfileId());
		Query addressRemovalQuery = new Query();
		addressRemovalQuery.addCriteria(Criteria.where(CommerceConstants._ID).is(addressItem.get().getId()));
		Address address = mongoOperation.findAndRemove(addressRemovalQuery , Address.class);
		account.get().removeAddress(addressItem.get());
		accounts.save(account.get());
		logger.debug("Address Document Removed Successfully :" , address.getId());
		if(null != addressItem && null != account){
			logger.debug("AccountService.removeAddress: Removed Address From Profile : "+addressKey);
			genericResponse.setStatus(Boolean.TRUE);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage("Address Removed Succesfully");
		}else{
			logger.error("AccountService.removeAddress: There is Some Error While removing Address");
			throw new Exception("Some Error Occured While Removing Address");
		}
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
	public Response changePassword(PasswordBean password, Long profileId) throws Exception {
		Optional<Account> account = accounts.findById(profileId);
		Response genericResponse = new Response();
		if(!passwordEncoder.matches(password.getOldPassword(), account.get().getPassword())){
			logger.error("Incorrect Old Password for user : " + account.get().getUsername());
			throw new IllegalArgumentException("Incorrect Old password supplied");
		}
		
		if(!password.getNewPassword().equals(password.getConfirmPassword())){
			logger.error("Password and Confirm Password Not Match for user : " + account.get().getUsername());
			throw new IllegalArgumentException("Password and Confirm Password Does not match");
		}
		
		account.get().setPassword(passwordEncoder.encode(password.getNewPassword()));
		Account savedAccount = accounts.save(account.get());
		if(null != account){
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setStatus(Boolean.TRUE);
			genericResponse.setMessage("Password has been changed successfully for profile id : "+savedAccount.getId());
			
		}else{
			throw new Exception("There is some error while updating password");
		}
		return genericResponse;
	}

	/**
	 * 
	 * @param profileId
	 * @return
	 */
	public ProfileAddressResponseBean getAllProfileAddress(Long profileId) {
		ProfileAddressResponseBean addressResponse = new ProfileAddressResponseBean();

		Optional<Account> account = accounts.findById(profileId);
		if (null == account) {
			
			addressResponse.setMessage("There is Some Issue With Address");
			addressResponse.setStatus(Boolean.FALSE);
			addressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return addressResponse;
		}

		List<Address> addressList = account.get().getAddresses();
		List<ProfileAddressResponse> addressResponseList = new ArrayList<ProfileAddressResponse>();
		if (null != addressList && !addressList.isEmpty()) {
			for (Address address : addressList) {
				ProfileAddressResponse response = new ProfileAddressResponse();
				response.setId(address.getId());
				try {
					BeanUtils.copyProperties(response, address);
				} catch (IllegalAccessException | InvocationTargetException e) {
				}
				addressResponseList.add(response);
			}
			addressResponse.setAdressResponse(addressResponseList);
			addressResponse.setMessage("All Profile Address Retrived Sucessfully");
			addressResponse.setStatus(Boolean.TRUE);
			addressResponse.setStatusCode(HttpStatus.OK.value());
		} else {
			addressResponse.setMessage("There is Some Issue With Address");
			addressResponse.setStatus(Boolean.FALSE);
			addressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return addressResponse;
	}
}
