package com.pallette.user.api;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
public class DisplayForgotPasswordRequest {

	@NotEmpty(message = "Please enter User Id")
	private Long profileId;

	@NotEmpty(message = "Please enter Token")
	private Long token;

	/**
	 * @return the token
	 */
	public Long getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(Long token) {
		this.token = token;
	}

	/**
	 * @return the profileId
	 */
	public Long getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId
	 *            the profileId to set
	 */
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

}
