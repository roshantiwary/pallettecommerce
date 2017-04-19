package com.pallette.user;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.DBObject;
import com.pallette.persistence.BaseEntity;
import com.pallette.user.api.AddEditAddressRequest;

@Document(collection = "user_address")
public class Address extends BaseEntity{

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

	public Address() {
		super();
	}

	public Address(AddEditAddressRequest address) {
		this(address.getId());
		this.emailAddress = address.getEmailAddress().toLowerCase();
		this.firstName = address.getFirstName();
		this.lastName = address.getLastName();
		this.address1 = address.getAddress1();
		this.address2 = address.getAddress2();
		this.city = address.getCity();
		this.state = address.getState();
		this.zipcode = address.getZipcode();
		this.phoneNumber = address.getPhoneNumber();
		this.profileId = address.getProfileId();
	}

	public Address(String id){
		super(id);
	}
	
	public Address(DBObject dbObject){
		this((String) dbObject.get("_id"));
		this.emailAddress = (String) dbObject.get("emailAddress");
		this.firstName = (String) dbObject.get("firstName");
		this.lastName = (String) dbObject.get("lastName");
		this.address1 = (String) dbObject.get("address1");
		this.address2 = (String) dbObject.get("address2");
		this.city = (String) dbObject.get("city");
		this.state = (String) dbObject.get("state");
		this.zipcode = (String) dbObject.get("zipcode");
		this.phoneNumber = (String) dbObject.get("phoneNumber");
		this.profileId = (String) dbObject.get("profileId");
	}
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
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
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	
	
}
