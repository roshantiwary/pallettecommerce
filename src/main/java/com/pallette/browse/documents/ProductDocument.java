/**
 * 
 */
package com.pallette.browse.documents;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pallette.persistence.BaseEntity;

/**
 * @author amall3
 *
 */
@Document(collection = "product")
public class ProductDocument extends BaseEntity {

	private static final long serialVersionUID = 1L;

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
	private ImagesDocument imagesDocument;

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

	/**
	 * @return the skuDocument
	 */
	public List<SkuDocument> getSkuDocument() {
		if (null == skuDocument)
			skuDocument = new ArrayList<SkuDocument>();
		return skuDocument;
	}

	/**
	 * @param skuDocument
	 *            the skuDocument to set
	 */
	public void setSkuDocument(List<SkuDocument> skuDocument) {
		this.skuDocument = skuDocument;
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

}
