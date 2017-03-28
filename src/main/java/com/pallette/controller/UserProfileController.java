package com.pallette.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.AccountBean;
import com.pallette.beans.AddressBean;
import com.pallette.response.GenericResponse;
import com.pallette.service.UserService;
import com.pallette.web.security.ApplicationUser;
@RestController
@RequestMapping("/private/rest/api/v1/userprofile")
public class UserProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
	@Autowired
	private AuthorizationServerTokenServices tokenServices;
	
	@Autowired
	private UserService accountService;
	
	@RequestMapping("/user")
	public ResponseEntity<GenericResponse> getProfile() {
		logger.debug("BrowseController.getAllProduct()");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			System.out.println("Logged-in User");
		} else {
			System.out.println("Anonymous user");
		}
		
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setStatusCode(HttpStatus.FOUND.value());
		genericResponse.setMessage("User Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
	}
	
	@RequestMapping("/admin")
	public ResponseEntity<GenericResponse> getAdmin(OAuth2Authentication oAuth2Authentication) {
		logger.debug("BrowseController.getAllProduct()");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> additionalInformation = tokenServices.getAccessToken(oAuth2Authentication).getAdditionalInformation();
		System.out.println(additionalInformation.get("profileId"));
		System.out.println(additionalInformation.get("orderId"));
		
		ApplicationUser user = (ApplicationUser) authentication.getPrincipal();
		String profileId = user.getProfileId();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
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
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
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
		
		Map<String, Object> additionalInformation = tokenServices.getAccessToken(oAuth2Authentication).getAdditionalInformation();
		System.out.println(additionalInformation.get("profileId"));
		System.out.println(additionalInformation.get("orderId"));
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			System.out.println("Logged-in User" + authentication.getName());
		} else {
			System.out.println("Anonymous user");
		}
		
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setStatusCode(HttpStatus.FOUND.value());
		genericResponse.setMessage("Administrator Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
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
	public ResponseEntity<GenericResponse> addNewAddress(@RequestBody AddressBean address) throws IllegalAccessException, InvocationTargetException {
		logger.info("Adding New Address in Profile : " + address.toString());
		GenericResponse genericResponse = null;
		
		genericResponse = accountService.addNewAddress(address);
		if(null != genericResponse && HttpStatus.OK.value() == genericResponse.getStatusCode()){
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
		}else{
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
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
	public ResponseEntity<GenericResponse> editAddress(@PathVariable("id") String addressKey,@RequestBody AddressBean address) throws IllegalAccessException, InvocationTargetException {
		logger.info("Editing Existing Address for Address Key : " + addressKey);
		GenericResponse genericResponse = null;
		
		genericResponse = accountService.editAddress(addressKey,address);
		if(null != genericResponse && HttpStatus.OK.value() == genericResponse.getStatusCode()){
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
		}else{
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
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
		GenericResponse genericResponse = null;
		
		genericResponse = accountService.removeAddress(addressKey);
		if(null != genericResponse && HttpStatus.OK.value() == genericResponse.getStatusCode()){
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
		}else{
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
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
	public ResponseEntity<GenericResponse> updateProfile(@RequestBody AccountBean account) throws IllegalAccessException, InvocationTargetException {
		logger.info("Profile update for id : " + account.getId());
		GenericResponse genericResponse = new GenericResponse();
		if(StringUtils.isEmpty(account.getUsername())){
			genericResponse.setMessage("Request is null");
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		genericResponse = accountService.updateProfile(account);
		if(null != genericResponse && HttpStatus.OK.value() == genericResponse.getStatusCode()){
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
		}else if(null != genericResponse && HttpStatus.ALREADY_REPORTED.value() == genericResponse.getStatusCode()){
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.ALREADY_REPORTED);
		}else{
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		
	}
}
