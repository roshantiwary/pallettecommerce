package com.pallette.beans;

import static org.springframework.util.Assert.notNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author vdwiv3
 *
 */
public class PasswordBean {
	
	private String id;
	
	private String oldPassword;
	
	private String newPassword;
	
	private String confirmPassword;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		notNull(id, "Mandatory argument 'id missing.'");
		this.id = id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		notNull(oldPassword, "Mandatory argument 'oldPassword missing.'");
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		notNull(newPassword, "Mandatory argument 'newPassword missing.'");
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		notNull(confirmPassword, "Mandatory argument 'confirmPassword missing.'");
		this.confirmPassword = confirmPassword;
	}
}
