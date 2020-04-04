package com.pallette.user;

import java.util.Date;

import com.pallette.beans.PasswordBean;
import com.pallette.beans.ProfileAddressResponseBean;
import com.pallette.domain.Address;
import com.pallette.response.Response;
import com.pallette.user.api.AddEditAddressRequest;
import com.pallette.user.api.ApiUser;
import com.pallette.user.api.CreateUserRequest;
import com.pallette.user.api.UpdateUserRequest;

public interface UserService {

	public ApiUser createUser(final CreateUserRequest createUserRequest);

	public ApiUser authenticate(String username, String password);

	public ApiUser getUser(Long userId);

	/**
	 * Save User
	 *
	 * @param userId
	 * @param request
	 */
	public ApiUser saveUser(Long userId, UpdateUserRequest request);
	
	public AddEditAddressRequest addNewAddress(AddEditAddressRequest request);
	
	public AddEditAddressRequest editAddress(AddEditAddressRequest request);

	public Response removeAddress(Long addressKey);

	public ProfileAddressResponseBean getAllProfileAddress(Long profileId);
	
	public Address getAddress(Long addressKey, Long profileId);

	public ApiUser updateProfile(UpdateUserRequest account, Long profileId);

	public ApiUser changePassword(PasswordBean password, Long profileId);

	/**
	 * Method that create and saves a new token document for Forgot Password
	 * Functionality.
	 * 
	 * @param user
	 * @param token
	 */
	public void createPasswordResetTokenForUser(User user, String token);

	/**
	 * Method that checks the validity/expiry of Password Reset Token.
	 * 
	 * @param expiryDate
	 */
	public boolean validatePasswordResetToken(Date expiryDate);
	
	/**
	 * Method for Updating the Password in case of Forget Password.
	 * 
	 * @param profileId
	 * @param newPassword
	 * @param oldPassword
	 * @return
	 */
	public ApiUser setNewPassword(Long profileId , String newPassword , String confirmPassword);
}
