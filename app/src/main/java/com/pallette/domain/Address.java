/**
 * 
 */
package com.pallette.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pallette.user.api.AddEditAddressRequest;

/**
 * @author amall3
 *
 */

@Document(collection = "address")
public class Address implements Serializable {

	@Id
	private String id;

	@Field(value = "shipping_group_type")
	private String shippingGroupType;

	private String prefix;

	private String firstName;

	private String middleName;

	private String lastName;

	private String suffix;

	private String address1;

	private String address2;

	private String address3;

	private String city;

	private String state;

	private String county;

	private String zipcode;

	private String country;

	private String profileId;
	
	private String phoneNumber;
	
	private String emailAddress;

	public Address() {
		super();
	}

	public Address(String id) {
		this.id = id;
	}

	public Address(AddEditAddressRequest address) {
		this.emailAddress = address.getEmailAddress() != null ? address.getEmailAddress().toLowerCase() : null;
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
	
	public String toString() {
		StringBuilder address = new StringBuilder();
		address.append("Address ").append("[").append("Email=")
				.append(getEmailAddress()).append(", ").append("Address1=")
				.append(getAddress1()).append(", ").append("Address2=")
				.append(getAddress2()).append(", ").append("Address3=")
				.append(getAddress3()).append(", ").append("City=")
				.append(getCity()).append(", ").append("State=")
				.append(getState()).append(", ").append("Country=")
				.append(getCountry()).append(", ").append("County=")
				.append(getCounty()).append(", ").append("FirstName=")
				.append(getFirstName()).append(", ").append("LastName=")
				.append(getLastName()).append(", ").append("MiddleName=")
				.append(getMiddleName()).append(", ").append("OwnerId=")
				.append(getProfileId()).append(", ").append("Prefix=")
				.append(getPrefix()).append(", ").append("Suffix=")
				.append(getSuffix()).append(", ").append("]");

		return address.toString();
	}

	public boolean equals(Object pAddress) {
		if (pAddress == null) {
			return false;
		}
		if (!(pAddress instanceof Address)) {
			return false;
		}
		Address address = (Address) pAddress;

		String aPrefix = getPrefix();
		String bPrefix = address.getPrefix();

		String aEmail = getEmailAddress();
		String bEmail = address.getEmailAddress();
		
		String aFirstName = getFirstName();
		String bFirstName = address.getFirstName();

		String aMiddleName = getMiddleName();
		String bMiddleName = address.getMiddleName();

		String aLastName = getLastName();
		String bLastName = address.getLastName();

		String aSuffix = getSuffix();
		String bSuffix = address.getSuffix();

		String aAddress1 = getAddress1();
		String bAddress1 = address.getAddress1();

		String aAddress2 = getAddress2();
		String bAddress2 = address.getAddress2();

		String aAddress3 = getAddress3();
		String bAddress3 = address.getAddress3();

		String aCity = getCity();
		String bCity = address.getCity();

		String aState = getState();
		String bState = address.getState();

		String aPostalCode = getZipcode();
		String bPostalCode = address.getZipcode();

		String aCountry = getCountry();
		String bCountry = address.getCountry();

		String aOwnerId = getProfileId();
		String bOwnerId = address.getProfileId();

		if ((aPrefix == null) && (bPrefix != null)) {
			return false;
		}
		
		if ((aEmail == null) && (bEmail != null)) {
			return false;
		}
		
		if ((aFirstName == null) && (bFirstName != null)) {
			return false;
		}
		if ((aMiddleName == null) && (bMiddleName != null)) {
			return false;
		}
		if ((aLastName == null) && (bLastName != null)) {
			return false;
		}
		if ((aSuffix == null) && (bSuffix != null)) {
			return false;
		}
		if ((aAddress1 == null) && (bAddress1 != null)) {
			return false;
		}
		if ((aAddress2 == null) && (bAddress2 != null)) {
			return false;
		}
		if ((aAddress3 == null) && (bAddress3 != null)) {
			return false;
		}
		if ((aCity == null) && (bCity != null)) {
			return false;
		}
		if ((aState == null) && (bState != null)) {
			return false;
		}
		if ((aPostalCode == null) && (bPostalCode != null)) {
			return false;
		}
		if ((aCountry == null) && (bCountry != null)) {
			return false;
		}
		if ((aOwnerId == null) && (bOwnerId != null)) {
			return false;
		}

		return (((aPrefix == null) && (bPrefix == null)) || ((aPrefix
				.equals(bPrefix)) && (((aEmail == null) && (bEmail == null)) || ((aEmail
				.equals(bEmail)) && ((((aFirstName == null) && (bFirstName == null)) || ((aFirstName
				.equals(bFirstName)) && ((((aMiddleName == null) && (bMiddleName == null)) || ((aMiddleName
				.equals(bMiddleName)) && ((((aLastName == null) && (bLastName == null)) || ((aLastName
				.equals(bLastName)) && ((((aSuffix == null) && (bSuffix == null)) || ((aSuffix
				.equals(bSuffix)) && ((((aAddress1 == null) && (bAddress1 == null)) || ((aAddress1
				.equals(bAddress1)) && ((((aAddress2 == null) && (bAddress2 == null)) || ((aAddress2
				.equals(bAddress2)) && ((((aAddress3 == null) && (bAddress3 == null)) || ((aAddress3
				.equals(bAddress3)) && ((((aCity == null) && (bCity == null)) || ((aCity
				.equals(bCity)) && ((((aState == null) && (bState == null)) || ((aState
				.equals(bState)) && ((((aPostalCode == null) && (bPostalCode == null)) || ((aPostalCode
				.equals(bPostalCode)) && ((((aCountry == null) && (bCountry == null)) || ((aCountry
				.equals(bCountry)) && ((((aOwnerId == null) && (bOwnerId == null)) || ((aOwnerId != null) && (aOwnerId
				.equals(bOwnerId))))))))))))))))))))))))))))))))))))))))));
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the shippingGroupType
	 */
	public String getShippingGroupType() {
		return shippingGroupType;
	}

	/**
	 * @param shippingGroupType
	 *            the shippingGroupType to set
	 */
	public void setShippingGroupType(String shippingGroupType) {
		this.shippingGroupType = shippingGroupType;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix
	 *            the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the address3
	 */
	public String getAddress3() {
		return address3;
	}

	/**
	 * @param address3
	 *            the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county
	 *            the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}