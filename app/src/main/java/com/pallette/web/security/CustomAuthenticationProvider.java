package com.pallette.web.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import com.pallette.user.UserService;
import com.pallette.user.api.ApiUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService service;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getPrincipal() != null ? authentication.getPrincipal().toString() : null;
        String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
        try {
            // create an authentication request
            final ApiUser apiUser = this.service.authenticate(username, password);
            
            // Add Logic to populate Order-id
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, Arrays.<GrantedAuthority>asList(new SimpleGrantedAuthority("USER")));
            token.setDetails(apiUser);
            return token;

        } catch (Exception e) {
            throw new OAuth2Exception(e.getMessage(), e);
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
//	List<GrantedAuthority> grantedAuths = new ArrayList<>();
//	if (params != null) {
//		
//		List<Role> roles = (List<Role>) params.get("role");
//		Iterator<Role> roleIter = roles.iterator(); 
//		while(roleIter.hasNext()) {
//			Role role = roleIter.next();
//			grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
//		}
}
