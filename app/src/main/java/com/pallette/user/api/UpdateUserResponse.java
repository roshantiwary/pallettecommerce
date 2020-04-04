package com.pallette.user.api;

import static org.springframework.util.Assert.notNull;

import com.pallette.response.Response;

public class UpdateUserResponse extends Response{

	UpdateUserRequest user;

	public UpdateUserResponse() {
		
	}

	public UpdateUserResponse(UpdateUserRequest user) {
		notNull(user, "Mandatory argument 'address' missing.");
		this.user = user;
	}

	public UpdateUserRequest getUser() {
		return user;
	}

	public void setUser(UpdateUserRequest user) {
		this.user = user;
	}
}
