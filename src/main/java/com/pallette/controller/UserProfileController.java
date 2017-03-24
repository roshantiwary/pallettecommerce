package com.pallette.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pallette.response.GenericResponse;
import com.pallette.web.security.ApplicationUser;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
@RestController
@RequestMapping("/private/rest/api/v1/userprofile")
public class UserProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
	@Autowired
	private AuthorizationServerTokenServices tokenServices;
	
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
}
