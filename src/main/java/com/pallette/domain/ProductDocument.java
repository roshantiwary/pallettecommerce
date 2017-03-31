/**
 * 
 */
package com.pallette.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author amall3
 *
 */
@Document(collection = "product")
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
	private CategoryDocument categoryDocument;
	
	@DBRef
	private List<SkuDocument> skuDocument;

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

	public List<SkuDocument> getSkuDocument() {
		if(null == skuDocument)
			skuDocument = new ArrayList<SkuDocument>();
		return skuDocument;
	}

	public void setSkuDocument(List<SkuDocument> skuDocument) {
		this.skuDocument = skuDocument;
	}

}
