package com.pallette.web.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import com.pallette.service.UserService;

import com.pallette.domain.AuthenticationRequest;
import com.pallette.domain.Role;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService service;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
		AuthenticationRequest request = new AuthenticationRequest();
		request.setUsername(name);
		request.setPassword(password);
		try {
			Map<String, Object> params = service.login(request);
			List<GrantedAuthority> grantedAuths = new ArrayList<>();
			if (params != null) {
				
				List<Role> roles = (List<Role>) params.get("role");
				Iterator<Role> roleIter = roles.iterator(); 
				while(roleIter.hasNext()) {
					Role role = roleIter.next();
					grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
				}
				
				// Add Logic to populate Order-id
				String profileId = (String) params.get("accountid");
				ApplicationUser appUser = new ApplicationUser(name, password, true, true, true, true, grantedAuths, profileId);
				
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
						appUser, password, grantedAuths);
				return auth;
			} else {
				throw new BadCredentialsException("Username not found");
			}
		} catch (HttpServerErrorException e) {
			throw new BadCredentialsException("Login failed!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
