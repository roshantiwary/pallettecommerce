package com.pallette.beans;

import com.pallette.domain.CategoryDocument;

/**
 * @author vdwiv3
 *
 */
public class CategoryResponse {

	private String id;

	private String categoryTitle;

	private String categoryDescription;

	private String categoryStatus;
	
	private ImageResponse imageResponse;

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
}
