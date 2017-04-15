package com.pallette.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.mongodb.DBObject;
import com.pallette.user.Role;
import com.pallette.persistence.BaseEntity;
import com.pallette.user.api.ApiUser;

@Document(collection = "app_user")
public class User extends BaseEntity implements UserDetails {

	private String emailAddress;
	private String firstName;
	private String lastName;
	private Integer age;
	private String hashedPassword;
	private Boolean verified = false;
	private List<Role> roles = new ArrayList<Role>();
		
	public User() {
		super();
	}

	public User(String id) {
		super(id);
	}

	public User(final ApiUser apiUser, final String hashedPassword, Role role) {
		this(apiUser.getId());
		this.emailAddress = apiUser.getEmailAddress().toLowerCase();
		this.hashedPassword = hashedPassword;
		this.firstName = apiUser.getFirstName();
		this.lastName = apiUser.getLastName();
		this.age = apiUser.getAge();
		this.roles.add(role);
	}

	public User(DBObject dbObject) {
		this((String) dbObject.get("_id"));
		this.emailAddress = (String) dbObject.get("emailAddress");
		this.firstName = (String) dbObject.get("firstName");
		this.lastName = (String) dbObject.get("lastName");
		this.hashedPassword = (String) dbObject.get("hashedPassword");
		this.verified = (Boolean) dbObject.get("verified");
		List<String> roles = (List<String>) dbObject.get("roles");
		deSerializeRoles(roles);
	}

	private void deSerializeRoles(List<String> roles) {
		for (String role : roles) {
			this.addRole(Role.valueOf(role));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Role role : this.getRoles()) {
			GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
			authorities.add(authority);
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return hashedPassword;
	}

	@Override
	public String getUsername() {
		return getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public List<Role> getRoles() {
		return Collections.unmodifiableList(this.roles);
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public boolean hasRole(Role role) {
		return (this.roles.contains(role));
	}

}
