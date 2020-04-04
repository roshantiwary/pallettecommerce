package com.pallette.user.api;

import com.pallette.response.Response;

public class DisplayForgotPasswordResponse extends Response {

	private Long profileId;

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
