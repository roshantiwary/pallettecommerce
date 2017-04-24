package com.pallette.controller;

import java.lang.reflect.InvocationTargetException;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.OrderResponse;
import com.pallette.beans.PasswordBean;
import com.pallette.beans.ProfileAddressResponse;
import com.pallette.beans.ProfileAddressResponseBean;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.constants.RestURLConstants;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.response.AddressResponse;
import com.pallette.response.ExceptionResponse;
import com.pallette.response.GenericResponse;
import com.pallette.response.OrderDetailResponse;
import com.pallette.response.Response;
import com.pallette.service.OrderService;
import com.pallette.user.Address;
import com.pallette.user.UserService;
import com.pallette.user.api.AddEditAddressRequest;
import com.pallette.user.api.AddEditAddressResponse;
import com.pallette.user.api.ApiUser;
import com.pallette.user.api.UpdateUserRequest;
import com.pallette.user.api.UpdateUserResponse;


@RestController
@RequestMapping("/private/rest/api/v1/userprofile")
public class UserProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	/**
	 * @param oAuth2Authentication
	 * @return
	 */
	@RequestMapping("/admin")
	public ResponseEntity<GenericResponse> getAdmin(OAuth2Authentication oAuth2Authentication) {
		logger.debug("UserProfileController.getAdmin()");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();

		if (!profileId.isEmpty()) {
			logger.debug("Logged-in User" + authentication.getName());
		} else {
			logger.debug("Anonymous user");
		}

		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setStatusCode(HttpStatus.FOUND.value());
		genericResponse.setMessage("Admin Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
	}

	/**
	 * @param oAuth2Authentication
	 * @return
	 */
	@RequestMapping("/administrator")
	public ResponseEntity<GenericResponse> getAdministrator(OAuth2Authentication oAuth2Authentication) {
		logger.debug("UserProfileController.getAdministrator()");
		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();
		
		if (!profileId.isEmpty()) {
			logger.debug("Logged-in User" + user.getName());
		} else {
			logger.debug("Anonymous user");
		}

		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setStatusCode(HttpStatus.FOUND.value());
		genericResponse.setMessage("Administrator Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
	}

	/**
	 * @param oAuth2Authentication
	 * @return
	 */
	@RequestMapping("/anonymous")
	public ResponseEntity<GenericResponse> getAnonymous(OAuth2Authentication oAuth2Authentication) {
		logger.debug("UserProfileController.getAnonymous()");

		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();

		if (!profileId.isEmpty()) {
			logger.debug("Logged-in User" + user.getName());
		} else {
			logger.debug("Anonymous user");
		}

		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setStatusCode(HttpStatus.FOUND.value());
		genericResponse.setMessage("Administrator Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
	}

	/**
	 * @param oAuth2Authentication
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("/user")
	public ResponseEntity<ApiUser> getProfile(OAuth2Authentication oAuth2Authentication)
			throws IllegalAccessException, InvocationTargetException {
		logger.debug("UserProfileController.getProfile()");

		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();

		ApiUser latestAPIUser = userService.getUser(profileId);
		if (!latestAPIUser.getId().isEmpty()) {
			return new ResponseEntity<>(latestAPIUser, new HttpHeaders(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(latestAPIUser, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * @param authentication
	 * @return
	 */
	private ApiUser getProfileId(OAuth2Authentication authentication) {
		
		ApiUser user = null;
		if(authentication.getUserAuthentication().getDetails() != null) {
			user = (ApiUser) authentication.getUserAuthentication().getDetails();
		}
		return user;
	}

	/**
	 * This Method adds the new address in user profile
	 * 
	 * @param address
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.PROFILE_ADD_ADDRESS_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddEditAddressResponse> addNewAddress(@Valid @RequestBody AddEditAddressRequest request, 
			OAuth2Authentication oAuth2Authentication){
		logger.debug("Adding New Address in Profile : " + request.toString());
		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();
		request.setProfileId(profileId);
		AddEditAddressRequest address = userService.addNewAddress(request);
		AddEditAddressResponse addAddressResponse = new AddEditAddressResponse(address);
		addAddressResponse.setMessage("Address Added Successfully");
		addAddressResponse.setStatus(Boolean.TRUE);
		addAddressResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity(addAddressResponse, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * This method updates the address details in user profile
	 * 
	 * @param addressKey
	 * @param address
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.PROFILE_EDIT_ADDRESS_URL, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddEditAddressResponse> editAddress(@Valid @RequestBody AddEditAddressRequest request){
		logger.debug("Editing Existing Address for Address Key : " + request.getId());
		AddEditAddressRequest address = userService.editAddress(request);
		AddEditAddressResponse editAddressResponse = new AddEditAddressResponse(address);
		editAddressResponse.setMessage("Address Updated Successfully");
		editAddressResponse.setStatus(Boolean.TRUE);
		editAddressResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity(editAddressResponse, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * This method updates the address details in user profile
	 * 
	 * @param addressKey
	 * @param address
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.PROFILE_GET_ADDRESS_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddressResponse> getAddress(@PathVariable("id") String addressKey,
			OAuth2Authentication oAuth2Authentication) throws IllegalAccessException, InvocationTargetException {
		logger.debug("Editing Existing Address for Address Key : " + addressKey);
		ProfileAddressResponse genericResponse = new ProfileAddressResponse();
		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();
	
		com.pallette.domain.Address address = userService.getAddress(addressKey, profileId);
		BeanUtils.copyProperties(genericResponse , address);
		genericResponse.setMessage("Address found");
		genericResponse.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * This Method removes the address from address repository and user profile
	 * 
	 * @param addressKey
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.POFILE_REMOVE_ADDRESS_URL, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> removeAddress(@PathVariable("id") String addressKey) {
		logger.debug("Editing Existing Address for Address Key : " + addressKey);
		Response genericResponse = new Response();
		genericResponse = userService.removeAddress(addressKey);
		genericResponse.setMessage("Address Removed Successfully");
		genericResponse.setStatus(Boolean.TRUE);
		genericResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * This Method updates the user's personal details
	 * 
	 * @param account
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.EDIT_PROFILE_URL, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateUserResponse> updateProfile(@Valid @RequestBody UpdateUserRequest account,OAuth2Authentication oAuth2Authentication){
		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();
		logger.debug("Profile update for id : " + profileId);
		user = userService.updateProfile(account,profileId);
		UpdateUserResponse response = new UpdateUserResponse(account);
		response.setMessage("Profile Updated Successfully");
		response.setStatus(Boolean.TRUE);
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity(response, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * This method updates the password
	 * 
	 * @param password
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.CHANGE_PASSWORD_URL, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> changePassword(@Valid @RequestBody PasswordBean password,
			OAuth2Authentication oAuth2Authentication) {
		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();
		logger.debug("Password update for id : " + profileId);
		Response genericResponse = new Response();
		user = userService.changePassword(password, profileId);
		genericResponse.setMessage("Password Updated Successfully");
		genericResponse.setStatus(Boolean.TRUE);
		genericResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> exceptionHandler(Exception ex) {
		ExceptionResponse error = new ExceptionResponse();
		error.setStatusCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(error, HttpStatus.OK);
	}

	/**
	 * This method fetch the order details of submitted order
	 * 
	 * @param password
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = RestURLConstants.ORDER_DETAIL_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable(CommerceConstants.ORDER_ID) String orderId)
			throws NoRecordsFoundException, IllegalAccessException, InvocationTargetException {

		logger.debug("Inside UserProfileController.getOrderDetail()");
		OrderDetailResponse orderResponse = new OrderDetailResponse();
		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No Order Id was Passed");

		logger.debug("Order Id from Request Body ", orderId);
		orderResponse = orderService.getOrderDetails(orderId);
		orderResponse.setStatus(Boolean.TRUE);
		orderResponse.setMessage("Order Detail Retrived Successfully");
		orderResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(orderResponse, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * This method fetch the order History of submitted order
	 * 
	 * @param password
	 * @return
	 * @throws NoRecordsFoundException
	 */
	@RequestMapping(value = RestURLConstants.ORDER_HISTORY_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderResponse> getOrderHistory(OAuth2Authentication oAuth2Authentication)
			throws NoRecordsFoundException {

		logger.debug("Inside UserProfileController.getOrderDetail()");
		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();
		OrderResponse response = new OrderResponse();
		if (StringUtils.isEmpty(profileId))
			throw new IllegalArgumentException("No Profile Id was Passed");

		logger.debug("Profile Id from Request Body ", profileId);
		response = orderService.getOrderHistory(profileId);
		response.setStatus(Boolean.TRUE);
		response.setMessage("Order History Retrived Successfully");
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * This method fetch the Saved Address of User Profile
	 * 
	 * @param password
	 * @return
	 */
	@RequestMapping(value = RestURLConstants.PROFILE_ADDRESSES_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProfileAddressResponseBean> getAllProfileAddress(OAuth2Authentication oAuth2Authentication) {

		logger.debug("Inside UserProfileController.getOrderDetail()");
		ApiUser user = getProfileId(oAuth2Authentication);
		String profileId = user.getId();
		ProfileAddressResponseBean response = new ProfileAddressResponseBean();
		if (StringUtils.isEmpty(profileId))
			throw new IllegalArgumentException("No Profile Id was Passed");

		logger.debug("Profile Id from Request Body ", profileId);
		response = userService.getAllProfileAddress(profileId);
		response.setMessage("Profile Addresses Retrived Successfully");
		response.setStatus(Boolean.TRUE);
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}
}
