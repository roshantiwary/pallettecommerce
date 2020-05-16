/**
 * 
 */
package com.pallette.domain;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pallette.config.CascadeSave;

/**
 * @author amall3
 *
 */

@Document(collection = "shippingGroup")
public class ShippingGroup implements Serializable {

	@Id
	private String id;

	@Field(value = "shipping_group_type")
	private String shippingGroupType;

	@Field(value = "shipping_group_method")
	private String shippingGroupMethod;

	private String description;

	private String state;
	
	@Field(value = "tracking_number")
	private String trackingNumber;
	
	@Field(value = "special_instructions")
	private String specialInstructions;

	@Field(value = "ship_on_date")
	private Date shipOnDate;

	@Field(value = "submitted_date")
	private Date submittedDate;
	
	@DBRef
	@CascadeSave
	private ShippingPriceInfo shippingPriceInfo;
	
	@DBRef
	@CascadeSave
	private Address address;

	public ShippingGroup() {
		super();
	}

	public ShippingGroup(String id) {
		this.id = id;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("shippingGroupType:").append(getShippingGroupType()).append("; ");
		buf.append("shippingGroupMethod:").append(getShippingGroupMethod()).append("; ");
		buf.append("description:").append(getDescription()).append("; ");
		buf.append("state:").append(getState()).append("; ");
		buf.append("shipOnDate:").append(getShipOnDate()).append("; ");
		buf.append("submittedDate:").append(getSubmittedDate()).append("; ");
		return buf.toString();
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
	 * @return the shippingGroupMethod
	 */
	public String getShippingGroupMethod() {
		return shippingGroupMethod;
	}

	/**
	 * @param shippingGroupMethod
	 *            the shippingGroupMethod to set
	 */
	public void setShippingGroupMethod(String shippingGroupMethod) {
		this.shippingGroupMethod = shippingGroupMethod;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the shipOnDate
	 */
	public Date getShipOnDate() {
		return shipOnDate;
	}

	/**
	 * @param shipOnDate
	 *            the shipOnDate to set
	 */
	public void setShipOnDate(Date shipOnDate) {
		this.shipOnDate = shipOnDate;
	}

	/**
	 * @return the submittedDate
	 */
	public Date getSubmittedDate() {
		return submittedDate;
	}

	/**
	 * @param submittedDate
	 *            the submittedDate to set
	 */
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	/**
	 * @return the shippingPriceInfo
	 */
	public ShippingPriceInfo getShippingPriceInfo() {
		return shippingPriceInfo;
	}

	/**
	 * @param shippingPriceInfo the shippingPriceInfo to set
	 */
	public void setShippingPriceInfo(ShippingPriceInfo shippingPriceInfo) {
		this.shippingPriceInfo = shippingPriceInfo;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}

	/**
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
}
