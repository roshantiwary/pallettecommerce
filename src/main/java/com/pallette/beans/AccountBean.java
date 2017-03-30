package com.pallette.beans;

import org.hibernate.validator.constraints.NotBlank;


/**
 * @author vdwiv3
 *
 */
public class AccountBean {

	@NotBlank(message="Please enter Id")
	private String id;

	@NotBlank(message="Please enter Username")
	private String username;
	
	@NotBlank(message="Please enter password")
	private String password;
	
	@NotBlank(message="Please enter Confirm Password")
	private String confirmPassword;

	@NotBlank(message="Please enter First Name")
	private String firstName;
	
	@NotBlank(message="Please enter Last Name")
	private String lastName;
	
    private String authtoken;
    
    private String phoneNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public String getAuthtoken() {
		return authtoken;
	}

	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
