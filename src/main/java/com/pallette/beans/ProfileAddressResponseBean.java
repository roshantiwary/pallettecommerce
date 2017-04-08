package com.pallette.beans;

import java.util.List;

import com.pallette.response.Response;

public class ProfileAddressResponseBean extends Response{

	private List<ProfileAddressResponse> adressResponse;

	public List<ProfileAddressResponse> getAdressResponse() {
		return adressResponse;
	}

	public void setAdressResponse(List<ProfileAddressResponse> adressResponse) {
		this.adressResponse = adressResponse;
	}
}
