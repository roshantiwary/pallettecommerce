package com.pallette.browse.response;

import java.util.List;

import com.pallette.response.Response;

/**
 * 
 * @author amall3
 *
 */
public class BrandResponseBean extends Response {

	private List<BrandBean> brands;

	/**
	 * @return the brands
	 */
	public List<BrandBean> getBrands() {
		return brands;
	}

	/**
	 * @param brands
	 *            the brands to set
	 */
	public void setBrands(List<BrandBean> brands) {
		this.brands = brands;
	}

}
