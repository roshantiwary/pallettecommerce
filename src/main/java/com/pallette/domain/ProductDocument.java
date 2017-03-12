/**
 * 
 */
package com.pallette.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author amall3
 *
 */
@Document(collection = "products")
public class ProductDocument {

	@Id
	private String id;

	@Field(value = "product_title")
	private String productTitle;

	@Field(value = "product_slug")
	private String productSlug;

	@Field(value = "product_description")
	private String productDescription;

	@DBRef
	private BrandDocument productBrand;

	@Field(value = "product_status")
	private String productStatus;

	@DBRef
	private ImagesDocument imagesDocument;

	@DBRef
	private PriceDocument priceDocument;

	@DBRef
	private InventoryDocument inventoryDocument;

	@DBRef
	private CategoryDocument categoryDocument;
	
	@Override
	public String toString() {
		return "Product{" + "id='" + id + '\'' + ", productTitle='"
				+ productTitle + '\'' + ", productDescription='"
				+ productDescription + '\'' + ", productBrand=" + productBrand
				+ ", productStatus=" + productStatus + '}';
	}

	/**
	 * @return the categoryDocument
	 */
	public CategoryDocument getCategoryDocument() {
		return categoryDocument;
	}

	/**
	 * @param categoryDocument
	 *            the categoryDocument to set
	 */
	public void setCategoryDocument(CategoryDocument categoryDocument) {
		this.categoryDocument = categoryDocument;
	}

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
	 * @return the productStatus
	 */
	public String getProductStatus() {
		return productStatus;
	}

	/**
	 * @param productStatus
	 *            the productStatus to set
	 */
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	/**
	 * @return the imagesDocument
	 */
	public ImagesDocument getImagesDocument() {
		return imagesDocument;
	}

	/**
	 * @param imagesDocument
	 *            the imagesDocument to set
	 */
	public void setImagesDocument(ImagesDocument imagesDocument) {
		this.imagesDocument = imagesDocument;
	}

	/**
	 * @return the priceDocument
	 */
	public PriceDocument getPriceDocument() {
		return priceDocument;
	}

	/**
	 * @param priceDocument
	 *            the priceDocument to set
	 */
	public void setPriceDocument(PriceDocument priceDocument) {
		this.priceDocument = priceDocument;
	}

	/**
	 * @return the inventoryDocument
	 */
	public InventoryDocument getInventoryDocument() {
		return inventoryDocument;
	}

	/**
	 * @param inventoryDocument
	 *            the inventoryDocument to set
	 */
	public void setInventoryDocument(InventoryDocument inventoryDocument) {
		this.inventoryDocument = inventoryDocument;
	}

	/**
	 * @return the productBrand
	 */
	public BrandDocument getProductBrand() {
		return productBrand;
	}

	/**
	 * @param productBrand
	 *            the productBrand to set
	 */
	public void setProductBrand(BrandDocument productBrand) {
		this.productBrand = productBrand;
	}

}
