package com.pallette.user.api;

import static org.springframework.util.Assert.notNull;

import com.pallette.response.Response;

public class AddEditAddressResponse extends Response{

	private AddEditAddressRequest address;

	public AddEditAddressResponse() {
		
	}

	public AddEditAddressResponse(AddEditAddressRequest address) {
		notNull(address, "Mandatory argument 'address' missing.");
		this.address = address;
	}

	public AddEditAddressRequest getAddress() {
		return address;
	}

	public void setAddress(AddEditAddressRequest address) {
		this.address = address;
	}
}
