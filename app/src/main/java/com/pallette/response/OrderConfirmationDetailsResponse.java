/**
 * 
 */
package com.pallette.response;

import java.util.Date;
import java.util.List;

/**
 * @author amall3
 *
 */
public class OrderConfirmationDetailsResponse extends Response {

	private String orderId;

	private String orderState;

	private double orderSubTotal;

	private Date submittedDate;
	
	private String profileId;

	private List<CartItemResponse> orderItems;

	private AddressResponse addressResponse;

	/**
	 * @return the orderItems
	 */
	public List<CartItemResponse> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems
	 *            the orderItems to set
	 */
	public void setOrderItems(List<CartItemResponse> orderItems) {
		this.orderItems = orderItems;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getOrderSubTotal() {
		return orderSubTotal;
	}

	public void setOrderSubTotal(double orderSubTotal) {
		this.orderSubTotal = orderSubTotal;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public AddressResponse getAddressResponse() {
		return addressResponse;
	}

	public void setAddressResponse(AddressResponse addressResponse) {
		this.addressResponse = addressResponse;
	}

	/**
	 * @return the orderState
	 */
	public String getOrderState() {
		return orderState;
	}

	/**
	 * @param orderState
	 *            the orderState to set
	 */
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	/**
	 * @return the profileId
	 */
	public String getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

}
