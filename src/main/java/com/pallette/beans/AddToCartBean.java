/**
 * 
 */
package com.pallette.beans;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author amall3
 *
 */
public class AddToCartBean {

	private String orderId;

	@NotBlank(message = "Product Id is mandatory.")
	private String productId;

	@NotNull(message = "Quantity is mandatory.")
	@Min(1)
	@Max(100)
	private long quantity;
	
	@NotBlank(message = "Sku Id is mandatory.")
	private String skuId;

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
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the quantity
	 */
	public long getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId
	 *            the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

}
