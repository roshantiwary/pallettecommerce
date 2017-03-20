/**
 * 
 */
package com.pallette.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author amall3
 *
 */

@Document(collection = "itemPriceInfo")
public class ItemPriceInfo extends AmountInfo {

	@Id
	private ObjectId id;

	@Field(value = "raw_total_price")
	private double rawTotalPrice;

	@Field(value = "list_price")
	private double listPrice;

	@Field(value = "sale_price")
	private double salePrice;

	@Field(value = "on_sale")
	private boolean onSale;

	public String toString() {
		StringBuffer buf = new StringBuffer(super.toString());
		buf.append("; ").append("rawTotalPrice:").append(getRawTotalPrice());
		buf.append("; ").append("listPrice:").append(getListPrice());
		buf.append("; ").append("salePrice:").append(getSalePrice());
		buf.append("; ").append("onSale:").append(isOnSale());
		return buf.toString();
	}

	public ItemPriceInfo() {
		super();
	}

	public ItemPriceInfo(ObjectId id) {
		this.id = id;
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
	 * @return the rawTotalPrice
	 */
	public double getRawTotalPrice() {
		return rawTotalPrice;
	}

	/**
	 * @param rawTotalPrice
	 *            the rawTotalPrice to set
	 */
	public void setRawTotalPrice(double rawTotalPrice) {
		this.rawTotalPrice = rawTotalPrice;
	}

	/**
	 * @return the listPrice
	 */
	public double getListPrice() {
		return listPrice;
	}

	/**
	 * @param listPrice
	 *            the listPrice to set
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	/**
	 * @return the salePrice
	 */
	public double getSalePrice() {
		return salePrice;
	}

	/**
	 * @param salePrice
	 *            the salePrice to set
	 */
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	/**
	 * @return the onSale
	 */
	public boolean isOnSale() {
		return onSale;
	}

	/**
	 * @param onSale
	 *            the onSale to set
	 */
	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}

}
