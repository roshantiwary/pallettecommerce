package com.pallette.user;

import static org.springframework.util.Assert.notNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	public ApiUser saveUser(String userId, UpdateUserRequest request) {
		validate(request);
        User user = userRepository.findById(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        if(request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if(request.getEmailAddress() != null) {
            if(!request.getEmailAddress().equals(user.getEmailAddress())) {
                user.setEmailAddress(request.getEmailAddress());
                user.setVerified(false);
            }
        }
        userRepository.save(user);
        return new ApiUser(user);
	}
	
	@Override
	@Transactional
	public ApiUser getUser(String userId) {
		Assert.notNull(userId);
        User user = userRepository.findById(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        return new ApiUser(user);
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
        String profileId = sequenceDao.getNextProfileSequenceId(SequenceConstants.SEQ_KEY);
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
		User user = userRepository.findOne(request.getProfileId());
		user.getShippingAddress().add(newAddress);
		userRepository.save(user);
		logger.debug("Added new address [{}].", newAddress.getId());
		return new AddEditAddressRequest(newAddress);
	}

	private com.pallette.domain.Address insertNewAddress(final AddEditAddressRequest request) {
		String addressId = sequenceDao.getNextAddressSequenceId(SequenceConstants.SEQ_KEY);
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
		com.pallette.domain.Address address = addressRepository.findOne(request.getId());
		address.setFirstName(request.getFirstName());
		address.setLastName(request.getLastName());
		address.setAddress1(request.getAddress1());
		address.setAddress2(request.getAddress2());
		address.setEmailAddress(request.getEmailAddress());
		address.setCity(request.getCity());
		address.setZipcode(request.getZipcode());
		address.setPhoneNumber(request.getPhoneNumber());
		logger.debug("Updated Address [{}].", address.getId());
		return new AddEditAddressRequest(addressRepository.save(address));
	}

	private void locateAddress(String addressKey) {
		notNull(addressKey, "Mandatory argument 'addressKey' missing.");
		com.pallette.domain.Address address = addressRepository.findOne(addressKey);
		if(null == address){
			logger.debug("Address not found for the key [{}]", addressKey);
            throw new AddressNotFoundException();
		}
	}

	@Override
	public Response removeAddress(String addressKey) {
		logger.debug("UserService.removeAddress: Removing Address : "+addressKey);
		
		Response genericResponse = new Response();
		locateAddress(addressKey);
		com.pallette.domain.Address addressItem = addressRepository.findOne(addressKey);
		User account = userRepository.findOne(addressItem.getProfileId());
		Query addressRemovalQuery = new Query();
		addressRemovalQuery.addCriteria(Criteria.where(CommerceConstants._ID).is(addressItem.getId()));
		com.pallette.domain.Address address = mongoOperation.findAndRemove(addressRemovalQuery , Address.class);
		account.removeAddress(address);
		userRepository.save(account);
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
	public ProfileAddressResponseBean getAllProfileAddress(String profileId) {
		ProfileAddressResponseBean addressResponse = new ProfileAddressResponseBean();

		User account = userRepository.findOne(profileId);
		if (null == account) {
			
			addressResponse.setMessage("There is Some Issue With Address");
			addressResponse.setStatus(Boolean.FALSE);
			addressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return addressResponse;
		}

		List<com.pallette.domain.Address> addressList = account.getShippingAddress();
		List<ProfileAddressResponse> addressResponseList = new ArrayList<ProfileAddressResponse>();
		if (null != addressList && !addressList.isEmpty()) {
			for (com.pallette.domain.Address address : addressList) {
				ProfileAddressResponse response = new ProfileAddressResponse();
				response.setId(address.getId().toString());
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
	public com.pallette.domain.Address getAddress(String addressKey, String profileId) {
		com.pallette.domain.Address address = addressRepository.findByProfileIdAndId(profileId, addressKey);
		if(null == address){
			logger.debug("Address not found for the key [{}]", addressKey);
            throw new AddressNotFoundException();
		}
		return address;
	}

	@Override
	public ApiUser updateProfile(UpdateUserRequest request, String profileId) {
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
	public ApiUser changePassword(PasswordBean password, String profileId) {
		validate(password);
		User user = userRepository.findOne(profileId);
		
		if(null == user)
			throw new UserNotFoundException();
		
		if(!passwordEncoder.matches(password.getOldPassword(), user.getPassword())){
			logger.error("Incorrect Old Password for user : " + user.getUsername());
			throw new IllegalArgumentException("Incorrect Old password supplied");
		}
		
		if(!password.getNewPassword().equals(password.getConfirmPassword())){
			logger.error("Password and Confirm Password Not Match for user : " + user.getUsername());
			throw new IllegalArgumentException("Password and Confirm Password Does not match");
		}
		
		user.setHashedPassword(passwordEncoder.encode(password.getNewPassword()));
		user = userRepository.save(user);
		return new ApiUser(user);
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
	public ApiUser setNewPassword(String profileId, String newPassword, String confirmPassword) {

		logger.info("Inside UserServiceImpl.setNewPassword()");
		logger.debug("Updating Password For {}", profileId);
		
		User user = userRepository.findOne(profileId);
		if (null == user)
			throw new UserNotFoundException();

		if (!newPassword.equals(confirmPassword)) {
			logger.error("Password and Confirm Password Not Match for user : " + user.getUsername());
			throw new IllegalArgumentException("Password and Confirm Password Does not match");
		}

		user.setHashedPassword(passwordEncoder.encode(newPassword));
		user = userRepository.save(user);
		return new ApiUser(user);
	}
}
