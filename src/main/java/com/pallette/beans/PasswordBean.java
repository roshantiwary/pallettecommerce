package com.pallette.beans;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author vdwiv3
 *
 */
public class PasswordBean {
	
	@NotBlank
	private String id;
	
	@NotBlank
	private String oldPassword;
	
	@NotBlank
	private String newPassword;
	
	@NotBlank
	private String confirmPassword;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
