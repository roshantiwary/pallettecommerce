/**
 * 
 */
package com.pallette.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.config.email.EmailConstants;
import com.pallette.config.email.EmailHtmlSender;
import com.pallette.config.email.EmailStatus;
import com.pallette.constants.RestURLConstants;
import com.pallette.response.Response;
import com.pallette.user.PasswordResetToken;
import com.pallette.user.PasswordResetTokenRepository;
import com.pallette.user.User;
import com.pallette.user.UserRepository;
import com.pallette.user.UserService;
import com.pallette.user.api.ApiUser;
import com.pallette.user.api.DisplayForgotPasswordRequest;
import com.pallette.user.api.DisplayForgotPasswordResponse;
import com.pallette.user.api.ForgotPasswordRequest;
import com.pallette.user.api.SetNewPasswordRequest;
import com.pallette.user.exception.UserNotFoundException;

/**
 * <p>
 * Controller that is invoked for Forgot Password Functionality.
 * </p>
 * 
 * @author amall3
 *=
 */
@RestController
@RequestMapping("/rest/api/v1")
public class PasswordController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EmailHtmlSender emailHtmlSender;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	/**
	 * Method responsible for creating a Reset Password Token document and
	 * triggering forgot Password Email with the reset url.
	 * 
	 * @param forgotPasswordRequest
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = RestURLConstants.FORGOT_PASSWORD_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> handleCreatePasswordResetTokenForUser(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest , Errors errors) {
		
		logger.info("Inside PasswordController.handleCreatePasswordResetTokenForUser()");
		Response response = new Response();
		
		// If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			response.setMessage(getValidationErrors(errors).toString());
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(response);
		}
		
		String emailAddress = forgotPasswordRequest.getEmailAddress();
		logger.debug("The Email Passed Is :: {}" , emailAddress);
		
		User user = userRepository.findByEmailAddress(emailAddress);
		if (null == user)
			throw new UserNotFoundException();
		
		
		String token = UUID.randomUUID().toString();
		logger.debug("The Token Created for Email {} is {}", emailAddress, token);
	
		userService.createPasswordResetTokenForUser(user, token);
		Context context = new Context();
		context.setVariable(EmailConstants.TITLE, "Forgot Password.");
		context.setVariable(EmailConstants.DESCRIPTION, "Forgot Password Email Description.");
		context.setVariable(EmailConstants.FORGOT_PASSWORD_URL, constructResetTokenEmail(token, user));
		 
		EmailStatus emailStatus = emailHtmlSender.send(emailAddress, "Forgot Password", "email/forgotPasswordEmailTemplate", context);
	
		if (emailStatus.isSuccess()) {
			response.setMessage("Forgot Password Email Successfully Sent.");
			response.setStatus(Boolean.FALSE);
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else {
			response.setMessage("Forgot Password Email Was Not Sent.");
			response.setStatus(Boolean.FALSE);
			response.setStatusCode(HttpStatus.FORBIDDEN.value());
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}
	}
	
	
	/**
	 * Method responsible for validating the parameters send in the password
	 * reset url and also the url expiry so that the user can proceed with
	 * change password.
	 * 
	 * @param displayForgotPasswordRequest
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = RestURLConstants.DISPLAY_FORGOT_PASSWORD, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DisplayForgotPasswordResponse> handleDisplayResetPasswordPage(@Valid @RequestBody DisplayForgotPasswordRequest displayForgotPasswordRequest , Errors errors) {
		
		logger.info("Inside PasswordController.handleDisplayResetPasswordPage()");
		DisplayForgotPasswordResponse response = new DisplayForgotPasswordResponse();
		
		// If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			response.setMessage(getValidationErrors(errors).toString());
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(response);
		}
		
		Long profileId = displayForgotPasswordRequest.getProfileId();
		Long token = displayForgotPasswordRequest.getToken();
		logger.debug("The Email Passed Is :: {} and Token is {}" , profileId , token);
		
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserIdAndToken(profileId , token);
		if (null == passwordResetToken) {
			response.setMessage("Oops!  This is an invalid password reset link.");
			response.setStatus(Boolean.FALSE);
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		}
		
		logger.debug("The Token Found is {}", passwordResetToken.getId());
		if (!userService.validatePasswordResetToken(passwordResetToken.getExpiryDate())) {
			response.setMessage("Oops!  The Password Reset Link has expired.");
			response.setStatus(Boolean.FALSE);
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(response);
		} else {
			response.setMessage("The Pasword Reset Link looks Good.");
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setProfileId(profileId);
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		}
	}
	
	/**
	 * Method responsible for changing the password in case of forget Password.
	 * 
	 * @param setNewPasswordRequest
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = RestURLConstants.SET_NEW_PASSWORD, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> handleSetNewPassword(@Valid @RequestBody SetNewPasswordRequest setNewPasswordRequest , Errors errors) {
		
		logger.info("Inside PasswordController.handleSetNewPassword()");
		Response response = new Response();
		
		// If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			response.setMessage(getValidationErrors(errors).toString());
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(response);
		}
		
		Long profileId = setNewPasswordRequest.getProfileId();
		logger.debug("Changing Password For :: {}", profileId);
		
		ApiUser user = userService.setNewPassword(profileId, setNewPasswordRequest.getNewPassword(), setNewPasswordRequest.getConfirmPassword());
		response.setMessage("Password was successfully set.");
		response.setStatus(Boolean.TRUE);
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param token
	 * @param user
	 * @return
	 */
	private String constructResetTokenEmail(final String token, final User user) {
		final String url = "/user/changePassword?id=" + user.getId() + "&token=" + token;
		return url;
	}
	
	/**
	 * Method that iterates the validation errors and returns a comma separated
	 * error message.
	 * 
	 * @param errors
	 * @return
	 */
	private StringBuilder getValidationErrors(Errors errors) {

		logger.info("Inside PasswordController.getValidationErrors()");
		StringBuilder errorMessages = new StringBuilder();
		
		for (ObjectError objErr : errors.getAllErrors()) {
			if (!StringUtils.isEmpty(errorMessages))
				logger.debug("Error Message is : ", objErr.getDefaultMessage());
			errorMessages = errorMessages.append(objErr.getDefaultMessage()).append(CommerceConstants.COMMA);
		}
		return errorMessages;
	}

}
