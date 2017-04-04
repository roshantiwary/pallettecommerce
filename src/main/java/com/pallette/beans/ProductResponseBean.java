package com.pallette.beans;

import java.util.List;

import com.pallette.response.Response;

/**
 * @author vdwiv3
 *
 */
public class ProductResponseBean extends Response{

	List<ProductResponse> productResponse;

	/**
	 * @return the productResponse
	 */
	public List<ProductResponse> getProductResponse() {
		return productResponse;
	}

	/**
	 * @param productResponse
	 *            the productResponse to set
	 */
	public void setProductResponse(List<ProductResponse> productResponse) {
		this.productResponse = productResponse;
	}
}
