package com.pallette.beans;

import com.pallette.domain.BrandDocument;
import com.pallette.domain.CategoryDocument;
import com.pallette.domain.ImagesDocument;

public class CategoryBean {

	private String id;

	private String categoryTitle;

	private String categorySlug;

	private String categoryDescription;

	private BrandDocument categoryBrand;

	private String categoryStatus;

	private ImagesDocument imagesDocument;

	private CategoryDocument parentCategoryDocument;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getCategorySlug() {
		return categorySlug;
	}

	public void setCategorySlug(String categorySlug) {
		this.categorySlug = categorySlug;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public BrandDocument getCategoryBrand() {
		return categoryBrand;
	}

	public void setCategoryBrand(BrandDocument categoryBrand) {
		this.categoryBrand = categoryBrand;
	}

	public String getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(String categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public ImagesDocument getImagesDocument() {
		return imagesDocument;
	}

	public void setImagesDocument(ImagesDocument imagesDocument) {
		this.imagesDocument = imagesDocument;
	}

	public CategoryDocument getParentCategoryDocument() {
		return parentCategoryDocument;
	}

	public void setParentCategoryDocument(CategoryDocument parentCategoryDocument) {
		this.parentCategoryDocument = parentCategoryDocument;
	}
}
