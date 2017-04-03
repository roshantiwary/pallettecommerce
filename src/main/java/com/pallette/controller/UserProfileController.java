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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.AccountBean;
import com.pallette.beans.AddressBean;
import com.pallette.beans.PasswordBean;
import com.pallette.domain.Account;
import com.pallette.response.ExceptionResponse;
import com.pallette.response.GenericResponse;
import com.pallette.response.AccountResponse;
import com.pallette.service.UserService;
import com.pallette.web.security.ApplicationUser;
@RestController
@RequestMapping("/private/rest/api/v1/userprofile")
public class UserProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
	@Autowired
	private UserService accountService;
	
	@RequestMapping("/admin")
	public ResponseEntity<GenericResponse> getAdmin(OAuth2Authentication oAuth2Authentication) {
		logger.debug("BrowseController.getAllProduct()");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = (ApplicationUser) authentication.getPrincipal();
		String profileId = user.getProfileId();
		
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
		logger.debug("BrowseController.getAllProduct()");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = (ApplicationUser) authentication.getPrincipal();
		String profileId = user.getProfileId();
		
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
		logger.debug("BrowseController.getAllProduct()");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = (ApplicationUser) authentication.getPrincipal();
		String profileId = user.getProfileId();
		
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
		logger.debug("UserProfileController.getUser()");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		ApplicationUser user = (ApplicationUser) authentication.getPrincipal();
		String profileId = user.getProfileId();
		
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
	
	/**
	 * This Method adds the new address in user profile
	 * 
	 * @param address
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/account/addresses", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> addNewAddress(@Valid @RequestBody AddressBean address) throws IllegalAccessException, InvocationTargetException {
		logger.info("Adding New Address in Profile : " + address.toString());
		GenericResponse genericResponse = new GenericResponse();
		
		try {
			genericResponse = accountService.addNewAddress(address);
			
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
	@RequestMapping(value = "/account/editAddress/{id}", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> editAddress(@PathVariable("id") String addressKey,@Valid @RequestBody AddressBean address) throws IllegalAccessException, InvocationTargetException {
		logger.info("Editing Existing Address for Address Key : " + addressKey);
		GenericResponse genericResponse = new GenericResponse();
		
		try {
			genericResponse = accountService.editAddress(addressKey,address);
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
	@RequestMapping(value = "/account/removeAddress/{id}", method = RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> removeAddress(@PathVariable("id") String addressKey) throws IllegalAccessException, InvocationTargetException {
		logger.info("Editing Existing Address for Address Key : " + addressKey);
		GenericResponse genericResponse = new GenericResponse();
		
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
	@RequestMapping(value = "/account/edit", method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> updateProfile(@Valid @RequestBody AccountBean account) throws IllegalAccessException, InvocationTargetException {
		logger.info("Profile update for id : " + account.getId());
		GenericResponse genericResponse = new GenericResponse();
		
		try {
			genericResponse = accountService.updateProfile(account);
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
	@RequestMapping(value = "/account/changePassword", method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> changePassword(@Valid @RequestBody PasswordBean password){
		logger.info("Password update for id : " + password.getId());
		GenericResponse genericResponse = new GenericResponse();
		try {
			genericResponse = accountService.changePassword(password);
		} catch (Exception e) {
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
}
