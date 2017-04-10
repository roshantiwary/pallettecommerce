package com.pallette.response;

import java.util.Date;
import java.util.List;

public class OrderDetailResponse extends Response{
	
	private String orderId;

	private List<OrderItemResponse> orderItems;

	private double orderSubTotal;
	
	private Date submittedDate;
	
	private AddressResponse addressResponse;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<OrderItemResponse> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemResponse> orderItems) {
		this.orderItems = orderItems;
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
}
