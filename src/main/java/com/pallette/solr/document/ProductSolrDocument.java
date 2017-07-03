package com.pallette.solr.document;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.pallette.browse.documents.BrandDocument;
import com.pallette.browse.documents.CategoryDocument;
import com.pallette.browse.documents.SkuDocument;

import org.apache.solr.client.solrj.beans.Field;

@SolrDocument(solrCoreName = "gettingstarted")
public class ProductSolrDocument {

	@Id
	@Field
	private String id;

	private Date timeCreated;

	@Field(value = "product_title")
	private String productTitle;

	@Field(value = "product_slug")
	private String productSlug;

	@Field(value = "product_description")
	private String productDescription;

	@Field(value = "product_status")
	private String productStatus;

	@Field(value = "product_category")
	private CategoryDocument categoryDocument;
	
	@Field(value = "product_brand")
	private BrandDocument brandDocument;
	
	@Field(value = "product_sku")
	private List<SkuDocument> skuDocument;

	public ProductSolrDocument() {
		super();
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	public CategoryDocument getCategoryDocument() {
		return categoryDocument;
	}

	public void setCategoryDocument(CategoryDocument categoryDocument) {
		this.categoryDocument = categoryDocument;
	}

	public BrandDocument getBrandDocument() {
		return brandDocument;
	}

	public void setBrandDocument(BrandDocument brandDocument) {
		this.brandDocument = brandDocument;
	}

	public List<SkuDocument> getSkuDocument() {
		return skuDocument;
	}

	public void setSkuDocument(List<SkuDocument> skuDocument) {
		this.skuDocument = skuDocument;
	}
}
