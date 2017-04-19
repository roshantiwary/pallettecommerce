package com.pallette.user;

import static org.springframework.util.Assert.notNull;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pallette.constants.SequenceConstants;
import com.pallette.repository.SequenceDao;
import com.pallette.service.BaseService;
import com.pallette.user.api.AddEditAddressRequest;
import com.pallette.user.api.ApiAddress;
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
	public ApiAddress addNewAddress(AddEditAddressRequest request) {
		logger.info("Validating Address request.");
		validate(request);
		Address newAddress = insertNewAddress(request);
		User user = userRepository.findOne(request.getAddress().getProfileId());
		user.getShippingAddress().add(newAddress);
		userRepository.save(user);
		logger.debug("Added new address [{}].", newAddress.getId());
		return new ApiAddress(newAddress);
	}

	private Address insertNewAddress(final AddEditAddressRequest request) {
		ApiAddress address = request.getAddress();
		String addressId = sequenceDao.getNextAddressSequenceId(SequenceConstants.SEQ_KEY);
		address.setId(addressId);
		Address newAddress = new Address(address);
		return addressRepository.save(newAddress);
	}

	@Override
	@Transactional
	public ApiAddress editAddress(AddEditAddressRequest request) {
		logger.info("Validating Address request.");
		validate(request);
		locateAddress(request.getAddress().getId());
		Address updateAddress = new Address(request.getAddress());
		logger.debug("Updated Address [{}].", updateAddress.getId());
		return new ApiAddress(addressRepository.save(updateAddress));
	}

	private void locateAddress(String addressKey) {
		notNull(addressKey, "Mandatory argument 'addressKey' missing.");
		Address address = addressRepository.findOne(addressKey);
		if(null == address){
			logger.debug("Address not found for the key [{}]", addressKey);
            throw new AddressNotFoundException();
		}
	}

}
