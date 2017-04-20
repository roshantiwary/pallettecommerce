package com.pallette.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.AccountResponse;
import com.pallette.constants.RestURLConstants;
import com.pallette.domain.AuthenticationRequest;
import com.pallette.exception.SymbolNotFoundException;
import com.pallette.resource.BaseResource;
import com.pallette.response.ExceptionResponse;
import com.pallette.service.UserService;
import com.pallette.user.Role;
import com.pallette.user.api.ApiUser;
import com.pallette.user.api.CreateUserRequest;
import com.pallette.user.api.CreateUserResponse;

@RestController
@RequestMapping("/rest/api/v1")
public class LoginController extends BaseResource {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ClientDetailsService clientDetailsService;

	private AuthorizationServerTokenServices tokenServices;

	private UserService accountService;

	@Autowired
	public LoginController(final AuthorizationServerTokenServices defaultTokenServices, ClientDetailsService clientDetailsService) {
		this.tokenServices = defaultTokenServices;
		this.clientDetailsService = clientDetailsService;
	}

	@Autowired
	private com.pallette.user.UserService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHome(Model model) throws SymbolNotFoundException {
		if (!model.containsAttribute("login")) {
			model.addAttribute("login", new AuthenticationRequest());
		}

		// check if user is logged in!
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			logger.debug("User logged in: " + currentUserName);
			model.addAttribute("account", accountService.getAccount(currentUserName));
		}

		return "index";
	}

	@RequestMapping(value = RestURLConstants.LOGIN_PROFILE_URL, method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute(value = "loginForm") AuthenticationRequest login) {
		logger.info("Logging in, user: " + login.getUsername());
		// need to add account object to session?
		// CustomDetails userDetails =
		// (CustomDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.debug("Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		Map<String, Object> params = accountService.login(login);
		model.addAllAttributes(params);
		// logger.info("got user details, token: " + userDetails.getToken());
		return "index";
	}

	@RequestMapping(value = RestURLConstants.LOGIN_PROFILE_URL, method = RequestMethod.GET)
	public String getLogin(Model model, @ModelAttribute(value = "loginForm") AuthenticationRequest login) {
		logger.info("Logging in GET, user: " + login.getUsername());
		return "login";
	}

	@RequestMapping(value = RestURLConstants.LOGOUT_URL, method = RequestMethod.POST)
	public String postLogout(Model model, @ModelAttribute(value = "login") AuthenticationRequest login) {
		logger.info("Logout, user: " + login.getUsername());
		logger.info(model.asMap().toString());
		return "index";
	}

	/**
	 * This Method creates the user profile and saves the profile in repository
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = RestURLConstants.CREATE_PROFILE_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountResponse> register(@Valid @RequestBody CreateUserRequest request) {

		ApiUser user = service.createUser(request);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		CreateUserResponse createUserResponse = new CreateUserResponse(user,
				createTokenForNewUser(user.getId(), request.getPassword().getPassword(), authentication.getName(), user));
		
		createUserResponse.setMessage("User Created Successfully");
		createUserResponse.setStatus(Boolean.TRUE);
		createUserResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity(createUserResponse, new HttpHeaders(), HttpStatus.OK);
	}

	private OAuth2AccessToken createTokenForNewUser(String userId, String password, String clientId, ApiUser user) {
		String hashedPassword = passwordEncoder.encode(password);
		UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(userId,
				hashedPassword, Collections.singleton(new SimpleGrantedAuthority(Role.USER.toString())));
		userAuthentication.setDetails(user);
		ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);
		OAuth2Request oAuth2Request = createOAuth2Request(null, clientId,
				Collections.singleton(new SimpleGrantedAuthority(Role.USER.toString())), true,
				authenticatedClient.getScope(), null, null, null, null);
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
		return tokenServices.createAccessToken(oAuth2Authentication);
	}

	private OAuth2Request createOAuth2Request(Map<String, String> requestParameters, String clientId,
			Collection<? extends GrantedAuthority> authorities, boolean approved, Collection<String> scope,
			Set<String> resourceIds, String redirectUri, Set<String> responseTypes,
			Map<String, Serializable> extensionProperties) {
		return new OAuth2Request(requestParameters, clientId, authorities, approved,
				scope == null ? null : new LinkedHashSet<String>(scope), resourceIds, redirectUri, responseTypes,
				extensionProperties);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> exceptionHandler(Exception ex) {
		ExceptionResponse error = new ExceptionResponse();
		error.setStatusCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(error, HttpStatus.OK);
	}

}
