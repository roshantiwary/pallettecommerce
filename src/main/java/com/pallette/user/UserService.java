package com.pallette.user;

import com.pallette.beans.PasswordBean;
import com.pallette.beans.ProfileAddressResponseBean;
import com.pallette.response.Response;
import com.pallette.user.api.AddEditAddressRequest;
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
	
	public AddEditAddressRequest addNewAddress(AddEditAddressRequest request);
	
	public AddEditAddressRequest editAddress(AddEditAddressRequest request);

	public Response removeAddress(String addressKey);

	public ProfileAddressResponseBean getAllProfileAddress(String profileId);
	
	public Address getAddress(String addressKey, String profileId);

	public ApiUser updateProfile(UpdateUserRequest account, String profileId);

	public ApiUser changePassword(PasswordBean password, String profileId);
}
