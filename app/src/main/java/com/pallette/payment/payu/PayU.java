/**
 * 
 */
package com.pallette.payment.payu;

import java.io.Serializable;

/**
 * @author amall3
 *
 */
public class PayU implements Serializable {

	private String key;
	private String hash;
	private String txnid;
	private double amount;
	private String firstname;
	private String email;
	private String phone;
	private String productinfo;
	private String service_provider;
	private String furl;
	private String surl;
	private String curl;
	private String action;
	private String lastname;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String udf1;
	private String udf2;
	private String udf3;
	private String hashString;
	private String udf4;
	private String udf5;
	private String pg;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash
	 *            the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return the txnid
	 */
	public String getTxnid() {
		return txnid;
	}

	/**
	 * @param txnid
	 *            the txnid to set
	 */
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the productinfo
	 */
	public String getProductinfo() {
		return productinfo;
	}

	/**
	 * @param productinfo
	 *            the productinfo to set
	 */
	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}

	/**
	 * @return the service_provider
	 */
	public String getService_provider() {
		return service_provider;
	}

	/**
	 * @param service_provider
	 *            the service_provider to set
	 */
	public void setService_provider(String service_provider) {
		this.service_provider = service_provider;
	}

	/**
	 * @return the furl
	 */
	public String getFurl() {
		return furl;
	}

	/**
	 * @param furl
	 *            the furl to set
	 */
	public void setFurl(String furl) {
		this.furl = furl;
	}

	/**
	 * @return the surl
	 */
	public String getSurl() {
		return surl;
	}

	/**
	 * @param surl
	 *            the surl to set
	 */
	public void setSurl(String surl) {
		this.surl = surl;
	}

	/**
	 * @return the curl
	 */
	public String getCurl() {
		return curl;
	}

	/**
	 * @param curl
	 *            the curl to set
	 */
	public void setCurl(String curl) {
		this.curl = curl;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
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

	/**
	 * @return the udf1
	 */
	public String getUdf1() {
		return udf1;
	}

	/**
	 * @param udf1
	 *            the udf1 to set
	 */
	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	/**
	 * @return the udf2
	 */
	public String getUdf2() {
		return udf2;
	}

	/**
	 * @param udf2
	 *            the udf2 to set
	 */
	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	/**
	 * @return the udf3
	 */
	public String getUdf3() {
		return udf3;
	}

	/**
	 * @param udf3
	 *            the udf3 to set
	 */
	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	/**
	 * @return the hashString
	 */
	public String getHashString() {
		return hashString;
	}

	/**
	 * @param hashString
	 *            the hashString to set
	 */
	public void setHashString(String hashString) {
		this.hashString = hashString;
	}

	/**
	 * @return the udf4
	 */
	public String getUdf4() {
		return udf4;
	}

	/**
	 * @param udf4
	 *            the udf4 to set
	 */
	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	/**
	 * @return the udf5
	 */
	public String getUdf5() {
		return udf5;
	}

	/**
	 * @param udf5
	 *            the udf5 to set
	 */
	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	/**
	 * @return the pg
	 */
	public String getPg() {
		return pg;
	}

	/**
	 * @param pg
	 *            the pg to set
	 */
	public void setPg(String pg) {
		this.pg = pg;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
