package com.pallette.user;

import static org.springframework.util.Assert.notNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Validator;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pallette.beans.PasswordBean;
import com.pallette.beans.ProfileAddressResponse;
import com.pallette.beans.ProfileAddressResponseBean;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.constants.SequenceConstants;
import com.pallette.domain.Address;
import com.pallette.repository.SequenceDao;
import com.pallette.response.Response;
import com.pallette.service.BaseService;
import com.pallette.user.api.AddEditAddressRequest;
import com.pallette.user.api.ApiUser;
import com.pallette.user.api.CreateUserRequest;
import com.pallette.user.api.UpdateUserRequest;
import com.pallette.user.exception.AddressNotFoundException;
import com.pallette.user.exception.AuthenticationException;
import com.pallette.user.exception.DuplicateUserException;
import com.pallette.user.exception.UserNotFoundException;

@Component
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final int EXPIRATION = 60 * 24;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAddressRepository addressRepository;
	
	@Autowired
	private SequenceDao sequenceDao;
	
	@Autowired
	private MongoOperations mongoOperation; 

	@Autowired
	public UserServiceImpl(Validator validator) {
		super(validator);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return locateUser(username);
	}

	@Override
	@Transactional
	public ApiUser createUser(CreateUserRequest createUserRequest) {
		logger.info("Validating user request.");
		validate(createUserRequest);
		final String emailAddress = createUserRequest.getUser().getEmailAddress().toLowerCase();
		if (userRepository.findByEmailAddress(emailAddress) == null) {
			logger.info("User does not already exist in the data store - creating a new user [{}].", emailAddress);
			User newUser = insertNewUser(createUserRequest);
			logger.debug("Created new user [{}].", newUser.getEmailAddress());
			return new ApiUser(newUser);
		} else {
			logger.info("Duplicate user located, exception raised with appropriate HTTP response code.");
			throw new DuplicateUserException();
		}
	}

	@Override
	public ApiUser authenticate(String username, String password) {
		Assert.notNull(username);
		Assert.notNull(password);
		User user = locateUser(username);
		if (!passwordEncoder.matches(password, user.getHashedPassword())) {
			throw new AuthenticationException();
		}
		return new ApiUser(user);
	}

	@Override
	@Transactional
	public ApiUser saveUser(Long userId, UpdateUserRequest request) {
		validate(request);
        Optional<User> user = userRepository.findById(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        if(request.getFirstName() != null && user.isPresent()) {
            user.get().setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null && user.isPresent()) {
            user.get().setLastName(request.getLastName());
        }
        if(request.getEmailAddress() != null && user.isPresent()) {
            if(!request.getEmailAddress().equals(user.get().getEmailAddress())) {
                user.get().setEmailAddress(request.getEmailAddress());
                user.get().setVerified(false);
            }
        }
        userRepository.save(user.get());
        return new ApiUser(user.get());
	}
	
	@Override
	@Transactional
	public ApiUser getUser(Long userId) {
		Assert.notNull(userId);
        Optional<User> user = userRepository.findById(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        return new ApiUser(user.get());
	}
	
	/**
     * Locate the user and throw an exception if not found.
     *
     * @param username
     * @return a User object is guaranteed.
     * @throws AuthenticationException if user not located.
     */
    private User locateUser(final String username) {
        notNull(username, "Mandatory argument 'username' missing.");
        User user = userRepository.findByEmailAddress(username.toLowerCase());
        if (user == null) {
            logger.debug("Credentials [{}] failed to locate a user.", username.toLowerCase());
            throw new UsernameNotFoundException("failed to locate a user");
        }
        return user;
    }

    private User insertNewUser(final CreateUserRequest createUserRequest) {
        String hashedPassword = passwordEncoder.encode(createUserRequest.getPassword().getPassword());
        ApiUser user = createUserRequest.getUser();
        Long profileId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        user.setId(profileId);
        User newUser = new User(user, hashedPassword, Role.USER);
        return userRepository.save(newUser);
    }
    
    @Override
	@Transactional
	public AddEditAddressRequest addNewAddress(AddEditAddressRequest request) {
		logger.info("Validating Address request.");
		validate(request);
		com.pallette.domain.Address newAddress = insertNewAddress(request);
		Optional<User> user = userRepository.findById(request.getProfileId());
		user.get().getShippingAddress().add(newAddress);
		userRepository.save(user.get());
		logger.debug("Added new address [{}].", newAddress.getId());
		return new AddEditAddressRequest(newAddress);
	}

	private com.pallette.domain.Address insertNewAddress(final AddEditAddressRequest request) {
		Long addressId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
		request.setId(addressId);
		com.pallette.domain.Address newAddress = new com.pallette.domain.Address(request);
		return addressRepository.save(newAddress);
	}

	@Override
	@Transactional
	public AddEditAddressRequest editAddress(AddEditAddressRequest request) {
		logger.info("Validating Address request.");
		validate(request);
		locateAddress(request.getId());
		Optional<Address> address = addressRepository.findById(request.getId());
		address.get().setFirstName(request.getFirstName());
		address.get().setLastName(request.getLastName());
		address.get().setAddress1(request.getAddress1());
		address.get().setAddress2(request.getAddress2());
		address.get().setEmailAddress(request.getEmailAddress());
		address.get().setCity(request.getCity());
		address.get().setZipcode(request.getZipcode());
		address.get().setPhoneNumber(request.getPhoneNumber());
		logger.debug("Updated Address [{}].", address.get().getId());
		return new AddEditAddressRequest(addressRepository.save(address.get()));
	}

	private void locateAddress(Long addressKey) {
		notNull(addressKey, "Mandatory argument 'addressKey' missing.");
		Optional<Address> address = addressRepository.findById(addressKey);
		if(null == address){
			logger.debug("Address not found for the key [{}]", addressKey);
            throw new AddressNotFoundException();
		}
	}

	@Override
	public Response removeAddress(Long addressKey) {
		logger.debug("UserService.removeAddress: Removing Address : "+addressKey);
		
		Response genericResponse = new Response();
		locateAddress(addressKey);
		Optional<Address> addressItem = addressRepository.findById(addressKey);
		Optional<User> account = userRepository.findById(addressItem.get().getProfileId());
		Query addressRemovalQuery = new Query();
		addressRemovalQuery.addCriteria(Criteria.where(CommerceConstants._ID).is(addressItem.get().getId()));
		com.pallette.domain.Address address = mongoOperation.findAndRemove(addressRemovalQuery , Address.class);
		account.get().removeAddress(address);
		userRepository.save(account.get());
		logger.debug("Address Document Removed Successfully :" , address.getId());
		if(null != addressItem && null != account){
			logger.debug("UserService.removeAddress: Removed Address From Profile : "+addressKey);
			genericResponse.setStatus(Boolean.TRUE);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage("Address Removed Succesfully");
		}else{
			logger.error("UserService.removeAddress: There is Some Error While removing Address");
		}
		return genericResponse;
	}

	@Override
	public ProfileAddressResponseBean getAllProfileAddress(Long profileId) {
		ProfileAddressResponseBean addressResponse = new ProfileAddressResponseBean();

		Optional<User> account = userRepository.findById(profileId);
		if (null == account) {
			
			addressResponse.setMessage("There is Some Issue With Address");
			addressResponse.setStatus(Boolean.FALSE);
			addressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return addressResponse;
		}

		List<com.pallette.domain.Address> addressList = account.get().getShippingAddress();
		List<ProfileAddressResponse> addressResponseList = new ArrayList<ProfileAddressResponse>();
		if (null != addressList && !addressList.isEmpty()) {
			for (com.pallette.domain.Address address : addressList) {
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

	@Override
	public com.pallette.domain.Address getAddress(Long addressKey, Long profileId) {
		com.pallette.domain.Address address = addressRepository.findByProfileIdAndId(profileId, addressKey);
		if(null == address){
			logger.debug("Address not found for the key [{}]", addressKey);
            throw new AddressNotFoundException();
		}
		return address;
	}

	@Override
	public ApiUser updateProfile(UpdateUserRequest request, Long profileId) {
		logger.info("Validating Address request.");
		validate(request);
		final String emailAddress = request.getEmailAddress().toLowerCase();
		User user = locateUser(emailAddress);
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmailAddress(request.getEmailAddress());
		userRepository.save(user);
		return new ApiUser(user);
	}

	@Override
	public ApiUser changePassword(PasswordBean password, Long profileId) {
		validate(password);
		Optional<User> user = userRepository.findById(profileId);
		
		if(null == user)
			throw new UserNotFoundException();
		
		if(!passwordEncoder.matches(password.getOldPassword(), user.get().getPassword())){
			logger.error("Incorrect Old Password for user : " + user.get().getUsername());
			throw new IllegalArgumentException("Incorrect Old password supplied");
		}
		
		if(!password.getNewPassword().equals(password.getConfirmPassword())){
			logger.error("Password and Confirm Password Not Match for user : " + user.get().getUsername());
			throw new IllegalArgumentException("Password and Confirm Password Does not match");
		}
		
		user.get().setHashedPassword(passwordEncoder.encode(password.getNewPassword()));
		User savedUser = userRepository.save(user.get());
		return new ApiUser(savedUser);
	}
	

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {

		logger.info("Inside UserServiceImpl.createPasswordResetTokenForUser()");
		logger.debug("The User Passed in is {} and Token is {}" , user.getId() , token);
		
		PasswordResetToken pwdToken = passwordResetTokenRepository.findByUserId(user.getId());
		if (null == pwdToken) {
			PasswordResetToken passwordToken = new PasswordResetToken(token, user);
			passwordResetTokenRepository.save(passwordToken);
		} else {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(pwdToken.getId()));
			Update update = new Update();
			update.set("token", token);
			update.set("expiryDate", calculateExpiryDate(EXPIRATION));
			mongoOperation.upsert(query, update, PasswordResetToken.class);
		}
	}
	
	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	@Override
	public boolean validatePasswordResetToken(Date expiryDate) {

		logger.info("Inside UserServiceImpl.validatePasswordResetToken()");
		logger.debug("The Expiry Date for Token is {}", expiryDate);
		boolean isValid = Boolean.TRUE;

		final Calendar cal = Calendar.getInstance();
		if ((expiryDate.getTime() - cal.getTime().getTime()) <= 0) {
			isValid = Boolean.FALSE;
		}
		return isValid;
	}

	@Override
	public ApiUser setNewPassword(Long profileId, String newPassword, String confirmPassword) {

		logger.info("Inside UserServiceImpl.setNewPassword()");
		logger.debug("Updating Password For {}", profileId);
		
		Optional<User> user = userRepository.findById(profileId);
		if (null == user)
			throw new UserNotFoundException();

		if (!newPassword.equals(confirmPassword)) {
			logger.error("Password and Confirm Password Not Match for user : " + user.get().getUsername());
			throw new IllegalArgumentException("Password and Confirm Password Does not match");
		}

		user.get().setHashedPassword(passwordEncoder.encode(newPassword));
		User savedUser = userRepository.save(user.get());
		return new ApiUser(savedUser);
	}
}
