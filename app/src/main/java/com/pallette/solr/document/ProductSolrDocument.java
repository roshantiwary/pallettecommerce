package com.pallette.solr.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.apache.solr.client.solrj.beans.Field;

@SolrDocument
public class ProductSolrDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8576369317461910182L;

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

//	@Field(child=true, value = "product_category")
	@Field(value = "product_category")
	private String categoryDocument;
	
	public String getCategoryDocument() {
		return categoryDocument;
	}

	public void setCategoryDocument(String categoryDocument) {
		this.categoryDocument = categoryDocument;
	}

	public String getBrandDocument() {
		return brandDocument;
	}

	public void setBrandDocument(String brandDocument) {
		this.brandDocument = brandDocument;
	}

	public List<String> getSkuDocument() {
		return skuDocument;
	}

	public void setSkuDocument(List<String> skuDocument) {
		this.skuDocument = skuDocument;
	}


//	@Field(child=true, value = "product_brand")
	@Field(value = "product_brand")
	private String brandDocument;

//	@Field(child=true, value = "product_sku")
	@Field(value = "product_sku")
	private List<String> skuDocument;

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

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
