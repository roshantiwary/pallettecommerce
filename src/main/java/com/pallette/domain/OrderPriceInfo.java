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

@Document(collection = "orderPriceInfo")
public class OrderPriceInfo extends AmountInfo {

	@Id
	private ObjectId id;

	@Field(value = "raw_sub_total")
	private double rawSubTotal;

	private double tax;

	private double shipping;
	
	public double getTotal() {
		return Math.round(getAmount() + getShipping() + getTax());
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(super.toString());
		buf.append("; ").append("rawSubTotal:").append(getRawSubTotal());
		buf.append("; ").append("tax:").append(getTax());
		buf.append("; ").append("shipping:").append(getShipping());
		return buf.toString();
	}

	public OrderPriceInfo() {
		super();
	}

	public OrderPriceInfo(ObjectId id) {
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
	 * @return the rawSubTotal
	 */
	public double getRawSubTotal() {
		return rawSubTotal;
	}

	/**
	 * @param rawSubTotal
	 *            the rawSubTotal to set
	 */
	public void setRawSubTotal(double rawSubTotal) {
		this.rawSubTotal = rawSubTotal;
	}

	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax
	 *            the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	/**
	 * @return the shipping
	 */
	public double getShipping() {
		return shipping;
	}

	/**
	 * @param shipping
	 *            the shipping to set
	 */
	public void setShipping(double shipping) {
		this.shipping = shipping;
	}
}
