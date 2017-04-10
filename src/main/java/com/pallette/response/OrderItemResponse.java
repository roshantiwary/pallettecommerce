package com.pallette.response;

public class OrderItemResponse {

	private String catalogRefId;

	private long quantity;

	private String description;

	private String productId;

	private String productTitle;

	private String productSlug;

	private String productBrand;

	private String productImage;

	private double amount;
	
	private double itemAmount;

	public String getCatalogRefId() {
		return catalogRefId;
	}

	public void setCatalogRefId(String catalogRefId) {
		this.catalogRefId = catalogRefId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getProductSlug() {
		return productSlug;
	}

	public void setProductSlug(String productSlug) {
		this.productSlug = productSlug;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(double itemAmount) {
		this.itemAmount = itemAmount;
	}
}
