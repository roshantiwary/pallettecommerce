package com.pallette.beans;

import com.pallette.domain.BrandDocument;
import com.pallette.domain.CategoryDocument;
import com.pallette.domain.ImagesDocument;

/**
 * @author vdwiv3
 *
 */
public class CategoryBean {

	private String id;

	private String categoryTitle;

	private String categorySlug;

	private String categoryDescription;

	private BrandDocument categoryBrand;

	private String categoryStatus;

	private ImagesDocument imagesDocument;

	private CategoryDocument parentCategoryDocument;

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
	public void setParentCategoryDocument(CategoryDocument parentCategoryDocument) {
		this.parentCategoryDocument = parentCategoryDocument;
	}
}
