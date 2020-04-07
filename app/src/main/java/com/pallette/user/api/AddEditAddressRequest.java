package com.pallette.user.api;

import static org.springframework.util.Assert.notNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

import com.pallette.user.Address;

@XmlRootElement
public class AddEditAddressRequest {

	@Valid
	@Email
	private String emailAddress;
	
	private String firstName;
	
	private String lastName;
	
	private String address1;
	
	private String address2;
	
	private String city;
	
	private String state;
	
	private String zipcode;
	
	private String phoneNumber;
	
	private String profileId;
	
	private String id;


	public AddEditAddressRequest(com.pallette.domain.Address address) {
		this.emailAddress = address.getEmailAddress();
		this.firstName = address.getFirstName();
		this.lastName = address.getLastName();
		this.address1 = address.getAddress1();
		this.address2 = address.getAddress2();
		this.city = address.getCity();
		this.state = address.getState();
		this.zipcode = address.getZipcode();
		this.phoneNumber = address.getPhoneNumber();
		this.profileId = address.getProfileId();
		this.id = address.getId();
	}

	public AddEditAddressRequest() {
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		notNull(emailAddress, "Mandatory argument 'Email missing.'");
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		notNull(firstName, "Mandatory argument 'First Name missing.'");
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		notNull(lastName, "Mandatory argument 'Last Name missing.'");
		this.lastName = lastName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		notNull(address1, "Mandatory argument 'Address1 missing.'");
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		notNull(city, "Mandatory argument 'City missing.'");
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		notNull(state, "Mandatory argument 'State missing.'");
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		notNull(zipcode, "Mandatory argument 'Zipcode missing.'");
		this.zipcode = zipcode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		notNull(phoneNumber, "Mandatory argument 'Phone Number missing.'");
		this.phoneNumber = phoneNumber;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		notNull(id, "Mandatory argument 'Id missing.'");
		this.id = id;
	}
}
