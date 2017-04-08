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

import com.pallette.beans.AccountBean;
import com.pallette.beans.AccountResponse;
import com.pallette.beans.AddressBean;
import com.pallette.beans.AddressResponseBean;
import com.pallette.beans.OrderResponse;
import com.pallette.beans.PasswordBean;
import com.pallette.beans.ProfileAddressResponse;
import com.pallette.beans.ProfileAddressResponseBean;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.constants.RestURLConstants;
import com.pallette.domain.Account;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.response.AddressResponse;
import com.pallette.response.CartResponse;
import com.pallette.response.ExceptionResponse;
import com.pallette.response.GenericResponse;
import com.pallette.response.Response;
import com.pallette.service.OrderService;
import com.pallette.service.UserService;
import com.pallette.web.security.ApplicationUser;
import com.pallette.web.security.CustomCredentialsService.CustomDetails;
@RestController
@RequestMapping("/private/rest/api/v1/userprofile")
public class UserProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
	@Autowired
	private UserService accountService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/admin")
	public ResponseEntity<GenericResponse> getAdmin(OAuth2Authentication oAuth2Authentication) {
		logger.debug("UserProfileController.getAdmin()");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		
		if (!profileId.isEmpty()) {
			System.out.println("Logged-in User" + authentication.getName());
		} else {
			System.out.println("Anonymous user");
		}
		
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setStatusCode(HttpStatus.FOUND.value());
		genericResponse.setMessage("Admin Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
	}
	
	@RequestMapping("/administrator")
	public ResponseEntity<GenericResponse> getAdministrator() {
		logger.debug("UserProfileController.getAdministrator()");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		
		if (!profileId.isEmpty()) {
			System.out.println("Logged-in User" + authentication.getName());
		} else {
			System.out.println("Anonymous user");
		}
		
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setStatusCode(HttpStatus.FOUND.value());
		genericResponse.setMessage("Administrator Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
	}
	
	@RequestMapping("/anonymous")
	public ResponseEntity<GenericResponse> getAnonymous(OAuth2Authentication oAuth2Authentication) {
		logger.debug("UserProfileController.getAnonymous()");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		
		if (!profileId.isEmpty()) {
			System.out.println("Logged-in User" + authentication.getName());
		} else {
			System.out.println("Anonymous user");
		}
		
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setStatusCode(HttpStatus.FOUND.value());
		genericResponse.setMessage("Administrator Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
	}
	
	@RequestMapping("/user")
	public ResponseEntity<AccountResponse> getProfile(OAuth2Authentication oAuth2Authentication) throws IllegalAccessException, InvocationTargetException {
		logger.debug("UserProfileController.getProfile()");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		
		AccountResponse acctResponse = new AccountResponse();
		if (!profileId.isEmpty()) {
			Account account = accountService.getAccountByProfileId(profileId);
			BeanUtils.copyProperties(acctResponse , account);
			acctResponse.setMessage("Successfully retreived Profile details");
			acctResponse.setStatusCode(HttpStatus.OK.value());
			acctResponse.setStatus(true);
		} else {
			acctResponse.setMessage("Please login to access your profile details");
			acctResponse.setStatusCode(HttpStatus.FORBIDDEN.value());
			return ResponseEntity.badRequest().body(acctResponse);
		}
		
		return new ResponseEntity<>(acctResponse, new HttpHeaders(), HttpStatus.OK);
	}

	private String getProfileId(Authentication authentication) {
		String profileId;
		if(!(authentication.getPrincipal() instanceof CustomDetails)) {
			ApplicationUser user = (ApplicationUser) authentication.getPrincipal();
			profileId = user.getProfileId();	
		} else {
			CustomDetails details = (CustomDetails) authentication.getPrincipal();
			profileId = details.getProfileId();	
		}
		return profileId;
	}
	
	/**
	 * This Method adds the new address in user profile
	 * 
	 * @param address
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.PROFILE_ADD_ADDRESS_URL, method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddressResponse> addNewAddress(@Valid @RequestBody AddressBean address, OAuth2Authentication oAuth2Authentication) throws IllegalAccessException, InvocationTargetException {
		logger.debug("Adding New Address in Profile : " + address.toString());
		ProfileAddressResponse genericResponse = new ProfileAddressResponse();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		try {
			genericResponse = accountService.addNewAddress(address,profileId);
			
		} catch (Exception e) {
			genericResponse.setMessage(e.getMessage());
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * This method updates the address details in
	 * user profile
	 * 
	 * @param addressKey
	 * @param address
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.PROFILE_EDIT_ADDRESS_URL, method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddressResponse> editAddress(@PathVariable("id") String addressKey,@Valid @RequestBody AddressBean address, OAuth2Authentication oAuth2Authentication) 
			throws IllegalAccessException, InvocationTargetException {
		logger.debug("Editing Existing Address for Address Key : " + addressKey);
		ProfileAddressResponse genericResponse = new ProfileAddressResponse();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		try {
			genericResponse = accountService.editAddress(addressKey,address,profileId);
		} catch (Exception e) {
			genericResponse.setMessage(e.getMessage());
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * This method updates the address details in
	 * user profile
	 * 
	 * @param addressKey
	 * @param address
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.PROFILE_GET_ADDRESS_URL, method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddressResponse> getAddress(@PathVariable("id") String addressKey, OAuth2Authentication oAuth2Authentication) 
			throws IllegalAccessException, InvocationTargetException {
		logger.debug("Editing Existing Address for Address Key : " + addressKey);
		ProfileAddressResponse genericResponse = new ProfileAddressResponse();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		try {
			genericResponse = accountService.getAddress(addressKey,profileId);
		} catch (Exception e) {
			genericResponse.setMessage(e.getMessage());
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * This Method removes the address from address repository 
	 * and user profile
	 * 
	 * @param addressKey
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.REMOVE_ADDRESS_URL, method = RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> removeAddress(@PathVariable("id") String addressKey) throws IllegalAccessException, InvocationTargetException {
		logger.debug("Editing Existing Address for Address Key : " + addressKey);
		Response genericResponse = new Response();
		
		try {
			genericResponse = accountService.removeAddress(addressKey);
		} catch (Exception e) {
			genericResponse.setMessage(e.getMessage());
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * This Method updates the user's personal details
	 * 
	 * @param account
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.EDIT_PROFILE_URL, method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountResponse> updateProfile(@Valid @RequestBody AccountBean account, OAuth2Authentication oAuth2Authentication) 
			throws IllegalAccessException, InvocationTargetException {
		
		AccountResponse genericResponse = new AccountResponse();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		logger.debug("Profile update for id : " + profileId);
		try {
			genericResponse = accountService.updateProfile(account,profileId);
		} catch (Exception e) {
			genericResponse.setMessage(e.getMessage());
			genericResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * This method updates the password
	 * @param password
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = RestURLConstants.CHANGE_PASSWORD_URL, method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> changePassword(@Valid @RequestBody PasswordBean password, OAuth2Authentication oAuth2Authentication){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		logger.debug("Password update for id : " + profileId);
		Response genericResponse = new Response();
		try {
			genericResponse = accountService.changePassword(password,profileId);
		} catch (Exception e) {
			genericResponse.setStatus(Boolean.FALSE);
			genericResponse.setMessage(e.getMessage());
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception ex){
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
	 */
	@RequestMapping(value = RestURLConstants.ORDER_DETAIL_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartResponse> getOrderDetail(@PathVariable(CommerceConstants.ORDER_ID) String orderId) throws NoRecordsFoundException{
		
		logger.debug("Inside UserProfileController.getOrderDetail()");
		CartResponse cartResponse = new CartResponse();
		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No Order Id was Passed");
		
		logger.debug("Order Id from Request Body ", orderId);
		cartResponse = orderService.getCartDetails(orderId);
		cartResponse.setStatus(Boolean.TRUE);
		cartResponse.setMessage("Order Detail Retrived Successfully");
		cartResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(cartResponse, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * This method fetch the order History of submitted order
	 * 
	 * @param password
	 * @return
	 * @throws NoRecordsFoundException 
	 */
	@RequestMapping(value = RestURLConstants.ORDER_HISTORY_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderResponse> getOrderHistory(OAuth2Authentication oAuth2Authentication) throws NoRecordsFoundException{
		
		logger.debug("Inside UserProfileController.getOrderDetail()");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
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
	 * @throws NoRecordsFoundException 
	 */
	@RequestMapping(value = RestURLConstants.PROFILE_ADDRESSES_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProfileAddressResponseBean> getAllProfileAddress(OAuth2Authentication oAuth2Authentication){
		
		logger.debug("Inside UserProfileController.getOrderDetail()");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String profileId = null;
		profileId = getProfileId(authentication);
		ProfileAddressResponseBean response = new ProfileAddressResponseBean();
		if (StringUtils.isEmpty(profileId))
			throw new IllegalArgumentException("No Profile Id was Passed");
		
		logger.debug("Profile Id from Request Body ", profileId);
		response = accountService.getAllProfileAddress(profileId);
		response.setStatus(Boolean.TRUE);
		response.setMessage("Order History Retrived Successfully");
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}
}
