package com.pallette.beans;

import java.util.List;

import com.pallette.response.AddressResponse;
import com.pallette.response.Response;

public class AddressResponseBean extends Response{

	private List<AddressResponse> adressResponse;

	public List<AddressResponse> getAdressResponse() {
		return adressResponse;
	}

	public void setAdressResponse(List<AddressResponse> adressResponse) {
		this.adressResponse = adressResponse;
	}
}
