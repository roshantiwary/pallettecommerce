/**
 * 
 */
package com.pallette.solr.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author amall3
 *
 */
public class ProductSearchRequest {

	@NotBlank(message = "Product Title is mandatory.")
	private String productTitle;

	@NotBlank(message = "Product description is mandatory.")
	private String productDescription;

	@Min(0)
	private int startPage;

	@Max(100)
	private int endPage;

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
	 * @return the startPage
	 */
	public int getStartPage() {
		return startPage;
	}

	/**
	 * @param startPage
	 *            the startPage to set
	 */
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	/**
	 * @return the endPage
	 */
	public int getEndPage() {
		return endPage;
	}

	/**
	 * @param endPage
	 *            the endPage to set
	 */
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

}
