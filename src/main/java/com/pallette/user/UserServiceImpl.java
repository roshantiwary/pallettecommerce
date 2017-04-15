package com.pallette.user;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import static org.springframework.util.Assert.notNull;
import com.pallette.service.BaseService;
import com.pallette.user.api.ApiUser;
import com.pallette.user.api.CreateUserRequest;
import com.pallette.user.api.UpdateUserRequest;
import com.pallette.user.exception.AuthenticationException;
import com.pallette.user.exception.DuplicateUserException;
import com.pallette.user.exception.UserNotFoundException;

@Service
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository, Validator validator, PasswordEncoder passwordEncoder) {
		super(validator);
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
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
        User newUser = new User(createUserRequest.getUser(), hashedPassword, Role.USER);
        return userRepository.save(newUser);
    }

}
