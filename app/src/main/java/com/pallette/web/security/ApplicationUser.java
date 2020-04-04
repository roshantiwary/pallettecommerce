package com.pallette.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ApplicationUser extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String profileId;
	
	public String getProfileId() {
		return profileId;
	}

	public ApplicationUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, String profileId) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.profileId = profileId;
	}

}
