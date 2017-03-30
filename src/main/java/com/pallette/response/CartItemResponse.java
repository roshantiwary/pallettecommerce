/**
 * 
 */
package com.pallette.response;

/**
 * @author amall3
 *
 */
public class CartItemResponse {

	private String catalogRefId;

	private long quantity;

	private String description;

	private String productId;

	private String productTitle;

	private String productSlug;

	private String productDescription;

	private String productBrand;

	private String productImage;

	private double amount;

	/**
	 * @return the catalogRefId
	 */
	public String getCatalogRefId() {
		return catalogRefId;
	}

	/**
	 * @param catalogRefId
	 *            the catalogRefId to set
	 */
	public void setCatalogRefId(String catalogRefId) {
		this.catalogRefId = catalogRefId;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the productSlug
	 */
	public String getProductSlug() {
		return productSlug;
	}

	/**
	 * @param productSlug
	 *            the productSlug to set
	 */
	public void setProductSlug(String productSlug) {
		this.productSlug = productSlug;
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
	 * @return the productBrand
	 */
	public String getProductBrand() {
		return productBrand;
	}

	/**
	 * @param productBrand
	 *            the productBrand to set
	 */
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	/**
	 * @return the productImage
	 */
	public String getProductImage() {
		return productImage;
	}

	/**
	 * @param productImage
	 *            the productImage to set
	 */
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
