package com.pallette.user.api;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@XmlRootElement
public class PasswordRequest {

	@Length(min = 8, max = 30)
	@NotNull
	private String password;

	public PasswordRequest() {
	}

	public PasswordRequest(final String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
