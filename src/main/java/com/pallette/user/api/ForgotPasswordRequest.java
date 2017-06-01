package com.pallette.user.api;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
public class ForgotPasswordRequest {

	@Valid
	@Email
	@NotEmpty(message = "Please enter Email")
	private String emailAddress;

	@NotEmpty(message = "Please Pass the forgot Password Url.")
	private String forgotPasswordUrl;

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the forgotPasswordUrl
	 */
	public String getForgotPasswordUrl() {
		return forgotPasswordUrl;
	}

	/**
	 * @param forgotPasswordUrl
	 *            the forgotPasswordUrl to set
	 */
	public void setForgotPasswordUrl(String forgotPasswordUrl) {
		this.forgotPasswordUrl = forgotPasswordUrl;
	}

}
