package com.pallette.beans;

import java.util.Date;

/**
 * @author vdwiv3
 *
 */
public class OrderHistoryResponse {

	private String orderId;
	
	private Date submittedDate;
	
	private String state;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
