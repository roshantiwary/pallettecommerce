package com.pallette.controller;

import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.AccountBean;
import com.pallette.domain.AuthenticationRequest;
import com.pallette.exception.SymbolNotFoundException;
import com.pallette.response.ExceptionResponse;
import com.pallette.response.GenericResponse;
import com.pallette.service.UserService;

@RestController
@RequestMapping("/rest/api/v1")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService accountService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHome(Model model) throws SymbolNotFoundException {
		if (!model.containsAttribute("login")) {
			model.addAttribute("login", new AuthenticationRequest());
		}
		
		//check if user is logged in!
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    logger.debug("User logged in: " + currentUserName);
		    model.addAttribute("account",accountService.getAccount(currentUserName));
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	//@RequestMapping(value = "/login")
	public String login(Model model, @ModelAttribute(value="loginForm")AuthenticationRequest login) {
		logger.info("Logging in, user: " + login.getUsername());
		//need to add account object to session?
		//CustomDetails userDetails = (CustomDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.debug("Principal: " + SecurityContextHolder.getContext().
				   getAuthentication().getPrincipal());
		Map<String,Object> params = accountService.login(login);
		model.addAllAttributes(params);
		//logger.info("got user details, token: " + userDetails.getToken());
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin(Model model, @ModelAttribute(value="loginForm") AuthenticationRequest login) {
		logger.info("Logging in GET, user: " + login.getUsername());
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public String postLogout(Model model, @ModelAttribute(value="login") AuthenticationRequest login) {
		logger.info("Logout, user: " + login.getUsername());
		logger.info(model.asMap().toString());
		return "index";
	}

	/**
	 * This Method creates the user profile and saves 
	 * the profile in repository
	 * 
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/account/create", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> register(@Valid @RequestBody AccountBean account) throws Exception {
		logger.info("register: user:" + account.getUsername());
		GenericResponse genericResponse = new GenericResponse();
		try{
			genericResponse = accountService.createAccount(account);
		}catch(Exception e){
			logger.error("Exception Occured while creating profile");
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			genericResponse.setMessage(e.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		if(HttpStatus.ALREADY_REPORTED.value() == genericResponse.getStatusCode())
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.ALREADY_REPORTED);
		
		return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception ex){
		ExceptionResponse error = new ExceptionResponse();
        error.setStatusCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(error, HttpStatus.OK);
    }

}
