/**
 * 
 */
package com.pallette.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pallette.config.CascadeSave;

/**
 * @author amall3
 *
 */

@Document(collection = "commerceItem")
public class CommerceItem implements Serializable {

	@Id
	private ObjectId id;

	@Field(value = "commerce_item_type")
	private String commerceItemType;

	@Field(value = "catalog_id")
	private String catalogId;

	@Field(value = "catalog_ref_id")
	private String catalogRefId;

	private long quantity;

	private String description;

	private String state;
	
	@Field(value= "product_id")
	private String productId;

	@DBRef
	@CascadeSave
	private ItemPriceInfo itemPriceInfo;

	public CommerceItem() {
		super();
	}

	public CommerceItem(ObjectId id) {
		this.id = id;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("commerceItemType:").append(getCommerceItemType()).append("; ");
		buf.append("catalogId:").append(getCatalogId()).append("; ");
		buf.append("description:").append(getDescription()).append("; ");
		buf.append("state:").append(getState()).append("; ");
		buf.append("productId:").append(getProductId()).append(";");
		buf.append("catalogRefId:").append(getCatalogRefId()).append("; ");
		buf.append("quantity:").append(getQuantity()).append("; ");
		return buf.toString();
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the commerceItemType
	 */
	public String getCommerceItemType() {
		return commerceItemType;
	}

	/**
	 * @param commerceItemType
	 *            the commerceItemType to set
	 */
	public void setCommerceItemType(String commerceItemType) {
		this.commerceItemType = commerceItemType;
	}

	/**
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * @param catalogId
	 *            the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * @return the catalogRefId
	 */
	public String getCatalogRefId() {
		return catalogRefId;
	}

	/**
	 * @param catalogRefId
	 *            the catalogRefId to set
	 */
	public void setCatalogRefId(String catalogRefId) {
		this.catalogRefId = catalogRefId;
	}

	/**
	 * @return the quantity
	 */
	public long getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public ItemPriceInfo getItemPriceInfo() {
		return itemPriceInfo;
	}

	public void setItemPriceInfo(ItemPriceInfo itemPriceInfo) {
		this.itemPriceInfo = itemPriceInfo;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
