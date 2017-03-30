/**
 * 
 */
package com.pallette.response;

import java.util.List;

/**
 * @author amall3
 *
 */
public class CartResponse extends Response {

	private String orderId;

	private List<CartItemResponse> cartItems;

	private double orderSubTotal;

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the cartItems
	 */
	public List<CartItemResponse> getCartItems() {
		return cartItems;
	}

	/**
	 * @param cartItems
	 *            the cartItems to set
	 */
	public void setCartItems(List<CartItemResponse> cartItems) {
		this.cartItems = cartItems;
	}

	/**
	 * @return the orderSubTotal
	 */
	public double getOrderSubTotal() {
		return orderSubTotal;
	}

	/**
	 * @param orderSubTotal
	 *            the orderSubTotal to set
	 */
	public void setOrderSubTotal(double orderSubTotal) {
		this.orderSubTotal = orderSubTotal;
	}

}
