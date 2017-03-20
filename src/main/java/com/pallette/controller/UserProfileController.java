package com.pallette.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pallette.response.GenericResponse;

@RestController
@RequestMapping("/private/rest/api/v1/userprofile")
public class UserProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
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
		genericResponse.setStatusCode(HttpStatus.FOUND);
		genericResponse.setMessage("User Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
	}
	
	@RequestMapping("/admin")
	public ResponseEntity<GenericResponse> getAdmin() {
		logger.debug("BrowseController.getAllProduct()");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			System.out.println("Logged-in User" + authentication.getName());
		} else {
			System.out.println("Anonymous user");
		}
		
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setStatusCode(HttpStatus.FOUND);
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
		genericResponse.setStatusCode(HttpStatus.FOUND);
		genericResponse.setMessage("Administrator Logged In");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
	}
}
