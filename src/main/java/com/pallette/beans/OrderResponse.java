package com.pallette.beans;

import java.util.List;

import com.pallette.response.Response;

public class OrderResponse extends Response{

	private List<OrderHistoryResponse> orderHistory;

	public List<OrderHistoryResponse> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(List<OrderHistoryResponse> orderHistory) {
		this.orderHistory = orderHistory;
	}
	
}
