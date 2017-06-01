package com.pallette.user.api;

import com.pallette.response.Response;

public class DisplayForgotPasswordResponse extends Response {

	private String profileId;

	/**
	 * @return the profileId
	 */
	public String getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId
	 *            the profileId to set
	 */
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

}
