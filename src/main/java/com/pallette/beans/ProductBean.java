package com.pallette.beans;

import com.pallette.domain.BrandDocument;
import com.pallette.domain.CategoryDocument;
import com.pallette.domain.ImagesDocument;
import com.pallette.domain.InventoryDocument;
import com.pallette.domain.PriceDocument;

public class ProductBean {

	private String id;

	private String productTitle;

	private String productSlug;

	private String productDescription;

	private BrandDocument productBrand;

	private String productStatus;

	private ImagesDocument imagesDocument;

	private PriceDocument priceDocument;

	private InventoryDocument inventoryDocument;

	private CategoryDocument categoryDocument;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public BrandDocument getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(BrandDocument productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public ImagesDocument getImagesDocument() {
		return imagesDocument;
	}

	public void setImagesDocument(ImagesDocument imagesDocument) {
		this.imagesDocument = imagesDocument;
	}

	public PriceDocument getPriceDocument() {
		return priceDocument;
	}

	public void setPriceDocument(PriceDocument priceDocument) {
		this.priceDocument = priceDocument;
	}

	public InventoryDocument getInventoryDocument() {
		return inventoryDocument;
	}

	public void setInventoryDocument(InventoryDocument inventoryDocument) {
		this.inventoryDocument = inventoryDocument;
	}

	public CategoryDocument getCategoryDocument() {
		return categoryDocument;
	}

	public void setCategoryDocument(CategoryDocument categoryDocument) {
		this.categoryDocument = categoryDocument;
	}
}
