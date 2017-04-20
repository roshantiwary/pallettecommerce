package com.pallette.browse.response;

import java.util.List;

import com.pallette.beans.SkuResponse;

/**
 * @author vdwiv3
 *
 */
public class ProductResponse {

	private String id;

	private String productTitle;
	
	private String productSlug;

	private String productDescription;

	private CategoryResponse categoryResponse;
	
	private List<SkuResponse> skuResponse;
	
	private ImageResponse imageResponse;
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the productTitle
	 */
	public String getProductTitle() {
		return productTitle;
	}

	/**
	 * @param productTitle
	 *            the productTitle to set
	 */
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * @param productDescription
	 *            the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/**
	 * @return the skuResponse
	 */
	public List<SkuResponse> getSkuResponse() {
		return skuResponse;
	}

	/**
	 * @param skuResponse
	 *            the skuResponse to set
	 */
	public void setSkuResponse(List<SkuResponse> skuResponse) {
		this.skuResponse = skuResponse;
	}

	/**
	 * @return the imageResponse
	 */
	public ImageResponse getImageResponse() {
		return imageResponse;
	}

	/**
	 * @param imageResponse
	 *            the imageResponse to set
	 */
	public void setImageResponse(ImageResponse imageResponse) {
		this.imageResponse = imageResponse;
	}

	/**
	 * @return the categoryResponse
	 */
	public CategoryResponse getCategoryResponse() {
		return categoryResponse;
	}

	/**
	 * @param categoryResponse
	 *            the categoryResponse to set
	 */
	public void setCategoryResponse(CategoryResponse categoryResponse) {
		this.categoryResponse = categoryResponse;
	}

	/**
	 * @return the productSlug
	 */
	public String getProductSlug() {
		return productSlug;
	}

	/**
	 * @param productSlug the productSlug to set
	 */
	public void setProductSlug(String productSlug) {
		this.productSlug = productSlug;
	}
}
