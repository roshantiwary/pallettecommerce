package com.pallette.user;

import com.pallette.user.api.ApiUser;
import com.pallette.user.api.CreateUserRequest;
import com.pallette.user.api.UpdateUserRequest;

public interface UserService {

	public ApiUser createUser(final CreateUserRequest createUserRequest);

	public ApiUser authenticate(String username, String password);

	public ApiUser getUser(String userId);

	/**
	 * Save User
	 *
	 * @param userId
	 * @param request
	 */
	public ApiUser saveUser(String userId, UpdateUserRequest request);

}
