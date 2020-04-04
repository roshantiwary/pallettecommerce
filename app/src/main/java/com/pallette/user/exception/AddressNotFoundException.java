package com.pallette.user.exception;

import com.pallette.exception.BaseWebApplicationException;

public class AddressNotFoundException extends BaseWebApplicationException{

	public AddressNotFoundException() {
		super(404, "Address Not Found", "No Address could be found for that Id");
	}

}
