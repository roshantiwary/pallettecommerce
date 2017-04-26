/**
 * 
 */
package com.pallette.browse.documents;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pallette.persistence.BaseEntity;

/**
 * @author amall3
 *
 */
@Document(collection = "price")
public class PriceDocument extends BaseEntity {

	@Field(value = "list_price")
	private double listPrice;

	@Field(value = "sale_price")
	private double salePrice;

	public PriceDocument() {
		super();
	}

	public PriceDocument(String id) {
		super(id);
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

}
