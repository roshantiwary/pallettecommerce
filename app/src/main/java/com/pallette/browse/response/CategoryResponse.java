package com.pallette.browse.response;

/**
 * @author vdwiv3
 *
 */
public class CategoryResponse {

	private String id;

	private String categoryTitle;

	private String categoryDescription;

	private String categoryStatus;

	private ImageResponse image;

	private CategoryResponse parentCategory;

	private String categorySlug;

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
	 * @return the image
	 */
	public ImageResponse getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(ImageResponse image) {
		this.image = image;
	}

	/**
	 * @return the parentCategory
	 */
	public CategoryResponse getParentCategory() {
		return parentCategory;
	}

	/**
	 * @param parentCategory
	 *            the parentCategory to set
	 */
	public void setParentCategory(CategoryResponse parentCategory) {
		this.parentCategory = parentCategory;
	}
}
