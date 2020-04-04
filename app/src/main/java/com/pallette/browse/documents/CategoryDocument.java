/**
 * 
 */
package com.pallette.browse.documents;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.pallette.persistence.BaseEntity;

/**
 * @author amall3
 *
 */

@Document(collection = "category")
public class CategoryDocument extends BaseEntity {

	private String categoryTitle;

	private String categorySlug;

	private String categoryDescription;

	@DBRef
	private BrandDocument categoryBrand;

	private String categoryStatus;

	@DBRef
	private ImagesDocument imagesDocument;

	@DBRef
	private CategoryDocument parentCategoryDocument;
	
	public CategoryDocument() {
		super();
	}

	/**
	 * @return the categoryTitle
	 */
	public String getCategoryTitle() {
		return categoryTitle;
	}

	/**
	 * @param categoryTitle
	 *            the categoryTitle to set
	 */
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	/**
	 * @return the categorySlug
	 */
	public String getCategorySlug() {
		return categorySlug;
	}

	/**
	 * @param categorySlug
	 *            the categorySlug to set
	 */
	public void setCategorySlug(String categorySlug) {
		this.categorySlug = categorySlug;
	}

	/**
	 * @return the categoryDescription
	 */
	public String getCategoryDescription() {
		return categoryDescription;
	}

	/**
	 * @param categoryDescription
	 *            the categoryDescription to set
	 */
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	/**
	 * @return the categoryStatus
	 */
	public String getCategoryStatus() {
		return categoryStatus;
	}

	/**
	 * @param categoryStatus
	 *            the categoryStatus to set
	 */
	public void setCategoryStatus(String categoryStatus) {
		this.categoryStatus = categoryStatus;
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
	 * @return the parentCategoryDocument
	 */
	public CategoryDocument getParentCategoryDocument() {
		return parentCategoryDocument;
	}

	/**
	 * @param parentCategoryDocument
	 *            the parentCategoryDocument to set
	 */
	public void setParentCategoryDocument(
			CategoryDocument parentCategoryDocument) {
		this.parentCategoryDocument = parentCategoryDocument;
	}

	/**
	 * @return the categoryBrand
	 */
	public BrandDocument getCategoryBrand() {
		return categoryBrand;
	}

	/**
	 * @param categoryBrand
	 *            the categoryBrand to set
	 */
	public void setCategoryBrand(BrandDocument categoryBrand) {
		this.categoryBrand = categoryBrand;
	}

}
