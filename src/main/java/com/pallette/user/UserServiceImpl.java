package com.pallette.user;

import static org.springframework.util.Assert.notNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pallette.beans.ProfileAddressResponse;
import com.pallette.beans.ProfileAddressResponseBean;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.constants.SequenceConstants;
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

	@Autowired
	private UserRepository userRepository;
	
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
		Address newAddress = insertNewAddress(request);
		User user = userRepository.findOne(request.getProfileId());
		user.getShippingAddress().add(newAddress);
		userRepository.save(user);
		logger.debug("Added new address [{}].", newAddress.getId());
		return new AddEditAddressRequest(newAddress);
	}

	private Address insertNewAddress(final AddEditAddressRequest request) {
		String addressId = sequenceDao.getNextAddressSequenceId(SequenceConstants.SEQ_KEY);
		request.setId(addressId);
		Address newAddress = new Address(request);
		return addressRepository.save(newAddress);
	}

	@Override
	@Transactional
	public AddEditAddressRequest editAddress(AddEditAddressRequest request) {
		logger.info("Validating Address request.");
		validate(request);
		locateAddress(request.getId());
		Address updateAddress = new Address(request);
		logger.debug("Updated Address [{}].", updateAddress.getId());
		return new AddEditAddressRequest(addressRepository.save(updateAddress));
	}

	private void locateAddress(String addressKey) {
		notNull(addressKey, "Mandatory argument 'addressKey' missing.");
		Address address = addressRepository.findOne(addressKey);
		if(null == address){
			logger.debug("Address not found for the key [{}]", addressKey);
            throw new AddressNotFoundException();
		}
	}

	@Override
	public Response removeAddress(String addressKey) {
		logger.debug("AccountService.removeAddress: Removing Address : "+addressKey);
		
		Response genericResponse = new Response();
		locateAddress(addressKey);
		Address addressItem = addressRepository.findOne(addressKey);
		User account = userRepository.findOne(addressItem.getProfileId());
		Query addressRemovalQuery = new Query();
		addressRemovalQuery.addCriteria(Criteria.where(CommerceConstants._ID).is(addressItem.getId()));
		Address address = mongoOperation.findAndRemove(addressRemovalQuery , Address.class);
		account.removeAddress(address);
		userRepository.save(account);
		logger.debug("Address Document Removed Successfully :" , address.getId());
		if(null != addressItem && null != account){
			logger.debug("AccountService.removeAddress: Removed Address From Profile : "+addressKey);
			genericResponse.setStatus(Boolean.TRUE);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage("Address Removed Succesfully");
		}else{
			logger.error("AccountService.removeAddress: There is Some Error While removing Address");
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

		List<Address> addressList = account.getShippingAddress();
		List<ProfileAddressResponse> addressResponseList = new ArrayList<ProfileAddressResponse>();
		if (null != addressList && !addressList.isEmpty()) {
			for (Address address : addressList) {
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

}
