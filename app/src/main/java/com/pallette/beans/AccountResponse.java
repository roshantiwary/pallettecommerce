package com.pallette.beans;

import java.util.ArrayList;
import java.util.List;

import com.pallette.domain.Address;
import com.pallette.response.Response;

/**
 * @author vdwiv3
 *
 */
public class AccountResponse extends Response{

	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private List<Address> address;
	
	private String phoneNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Address> getAddress() {
		if(null == address)
			address = new ArrayList<Address>();
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
