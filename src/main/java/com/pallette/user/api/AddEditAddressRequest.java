package com.pallette.user.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddEditAddressRequest {

	@NotNull
	@Valid
	ApiAddress address;

	public AddEditAddressRequest(ApiAddress address) {
		this.address = address;
	}

	public AddEditAddressRequest() {
	}

	public ApiAddress getAddress() {
		return address;
	}

	public void setAddress(ApiAddress address) {
		this.address = address;
	}
}
