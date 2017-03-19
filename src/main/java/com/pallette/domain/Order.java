/**
 * 
 */
package com.pallette.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.pallette.config.CascadeSave;

/**
 * @author amall3
 *
 */

@Document(collection = "order")
public class Order implements Serializable {

	@Id
	private String id;

	@Field(value = "order_type")
	private String orderType;

	@Field(value = "profile_id")
	private String profileId;

	private String description;

	private String state;

	@Field(value = "is_transient")
	private boolean isTransient;

	@Field(value = "special_instructions")
	private String specialInstructions;

	@Field(value = "created_date")
	private Date createdDate;

	@Field(value = "submitted_date")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date submittedDate;

	@Field(value = "origin_of_order")
	private String originOfOrder;

	@Field(value = "site_id")
	private String siteId;
	
	@DBRef
	@CascadeSave
	private List<CommerceItem> commerceItems;
	
	@DBRef
	@CascadeSave
	private List<ShippingGroup> shippingGroups;
	
	@DBRef
	@CascadeSave
	private List<PaymentGroup> paymentGroups;

	@DBRef
	@CascadeSave
	private OrderPriceInfo orderPriceInfo;

	public Order() {
		super();
	}

	public Order(String id) {
		this.id = id;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("orderType:").append(getOrderType()).append("; ");
		buf.append("profileId:").append(getProfileId()).append("; ");
		buf.append("description:").append(getDescription()).append("; ");
		buf.append("state:").append(getState()).append("; ");
		buf.append("isTransient:").append(isTransient()).append("; ");
		buf.append("specialInstructions:").append(getSpecialInstructions()).append("; ");
		buf.append("createdDate:").append(getCreatedDate()).append("; ");
		buf.append("submittedDate:").append(getSubmittedDate()).append("; ");
		buf.append("originOfOrder:").append(getOriginOfOrder()).append("; ");
		buf.append("siteId:").append(getSiteId()).append("; ");
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
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the profileId
	 */
	public String getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId
	 *            the profileId to set
	 */
	public void setProfileId(String profileId) {
		this.profileId = profileId;
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
	 * @return the isTransient
	 */
	public boolean isTransient() {
		return isTransient;
	}

	/**
	 * @param isTransient
	 *            the isTransient to set
	 */
	public void setTransient(boolean isTransient) {
		this.isTransient = isTransient;
	}

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param specialInstructions
	 *            the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	 * @return the originOfOrder
	 */
	public String getOriginOfOrder() {
		return originOfOrder;
	}

	/**
	 * @param originOfOrder
	 *            the originOfOrder to set
	 */
	public void setOriginOfOrder(String originOfOrder) {
		this.originOfOrder = originOfOrder;
	}

	/**
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId
	 *            the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	/**
	 * @return the orderPriceInfo
	 */
	public OrderPriceInfo getOrderPriceInfo() {
		return orderPriceInfo;
	}


	/**
	 * @param orderPriceInfo
	 *            the orderPriceInfo to set
	 */
	public void setOrderPriceInfo(OrderPriceInfo orderPriceInfo) {
		this.orderPriceInfo = orderPriceInfo;
	}

	/*
	 * Add Commerce Item.
	 */
	public void addCommerceItem(CommerceItem commerceItem) {
		if (null == this.commerceItems) {
			commerceItems = new ArrayList<CommerceItem>();
			commerceItems.add(commerceItem);
		} else {
			commerceItems.add(commerceItem);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<CommerceItem> getCommerceItems() {
		return commerceItems;
	}
	
	public void addShippingGroup(ShippingGroup shippingGroup) {
		if (null == this.shippingGroups) {
			shippingGroups = new ArrayList<ShippingGroup>();
			shippingGroups.add(shippingGroup);
		} else {
			shippingGroups.add(shippingGroup);
		}
	}

	public void addPaymentGroup(PaymentGroup paymentGroup) {
		if (null == this.paymentGroups) {
			paymentGroups = new ArrayList<PaymentGroup>();
			paymentGroups.add(paymentGroup);
		} else {
			paymentGroups.add(paymentGroup);
		}
	}


	public List<ShippingGroup> getShippingGroups() {
		return shippingGroups;
	}

	public List<PaymentGroup> getPaymentGroups() {
		return paymentGroups;
	}

}
