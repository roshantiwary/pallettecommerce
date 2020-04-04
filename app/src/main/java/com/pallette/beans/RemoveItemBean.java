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
	private Long orderId;

	@NotBlank(message = "Product Id is mandatory.")
	private Long productId;
	
	@NotBlank(message = "Sku Id is mandatory.")
	private Long skuId;

	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the skuId
	 */
	public Long getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId
	 *            the skuId to set
	 */
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

}
