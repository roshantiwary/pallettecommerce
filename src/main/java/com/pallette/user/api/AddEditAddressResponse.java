package com.pallette.user.api;

import static org.springframework.util.Assert.notNull;

import com.pallette.response.Response;

public class AddEditAddressResponse extends Response{

	private ApiAddress address;

	public AddEditAddressResponse() {
		
	}

	public AddEditAddressResponse(ApiAddress address) {
		notNull(address, "Mandatory argument 'address' missing.");
		this.address = address;
	}

	public ApiAddress getAddress() {
		return address;
	}

	public void setAddress(ApiAddress address) {
		this.address = address;
	}
}
