/**
 * 
 */
package com.pallette.beans;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author amall3
 *
 */
public class RemoveItemBean {

	@NotBlank(message = "Order Id is mandatory.")
	private String orderId;

	@NotBlank(message = "Product Id is mandatory.")
	private String productId;
	
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
